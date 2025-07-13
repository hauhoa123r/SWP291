// src/main/java/org/project/service/impl/WalletServiceImpl.java
package org.project.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import org.project.entity.PaymentEntity;
import org.project.entity.UserEntity;
import org.project.entity.WalletEntity;
import org.project.entity.WalletTransactionEntity;
import org.project.enums.PaymentMethod;
import org.project.enums.PaymentStatus;
import org.project.enums.WalletTransactionType;
import org.project.repository.PaymentRepository;
import org.project.repository.UserRepository;
import org.project.repository.WalletRepository;
import org.project.repository.WalletTransactionRepository;
import org.project.service.VNPayService;
import org.project.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private WalletTransactionRepository walletTransactionRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private VNPayService vnpayService;
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public WalletEntity getOrCreateWallet(Long userId) {
        System.out.println("DEBUG: getOrCreateWallet called for userId: " + userId);
        return walletRepository.findByUser_Id(userId).orElseGet(() -> {
            UserEntity user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
            WalletEntity newWallet = new WalletEntity();
            newWallet.setUser(user);
            newWallet.setBalance(BigDecimal.ZERO);
            WalletEntity savedWallet = walletRepository.save(newWallet);
            System.out.println("DEBUG: New wallet created and saved with ID: " + savedWallet.getId());
            return savedWallet;
        });
    }

    @Override
    @Transactional
    public String initiateVnpayDeposit(Long userId, BigDecimal amount, HttpServletRequest request) {
        System.out.println("DEBUG: initiateVnpayDeposit called for userId: " + userId + ", amount: " + amount);
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String transactionRef = UUID.randomUUID().toString();
        String orderInfo = "Nap tien vao vi cho user " + userId + ". Ref: " + transactionRef;

        PaymentEntity payment = new PaymentEntity();
        // CẢNH BÁO: orderEntity là NOT NULL trong PaymentEntity của bạn.
        // Nếu bạn chưa sửa PaymentEntity để cho phép orderEntity là NULL,
        // Dòng này sẽ gây lỗi DataIntegrityViolationException.
        payment.setAmount(amount);
        payment.setPaymentTime(Timestamp.valueOf(LocalDateTime.now()));
        payment.setPaymentMethod(PaymentMethod.VNPAY);
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setTransactionRef(transactionRef);
        payment.setDescription(orderInfo);

        try {
            PaymentEntity savedPayment = paymentRepository.save(payment); // Dòng này có thể gây lỗi
            System.out.println("DEBUG: PaymentEntity for deposit initiated and saved with ID: " + savedPayment.getId() + ", TxnRef: " + savedPayment.getTransactionRef());
        } catch (Exception e) {
            System.err.println("ERROR: Failed to save PaymentEntity during deposit initiation. This is likely due to orderEntity NOT NULL constraint. Error: " + e.getMessage());
            throw new RuntimeException("Failed to initiate deposit due to database error. Please check PaymentEntity's orderEntity constraint.", e);
        }


        return vnpayService.createPaymentUrl(transactionRef, orderInfo, amount, request);
    }

    @Override
    @Transactional
    public boolean handleVnpayCallback(HttpServletRequest request) {
        System.out.println("DEBUG: handleVnpayCallback called.");

        // 1. Xác thực chữ ký VNPAY
        if (!vnpayService.validateVnpayCallback(request)) {
            System.err.println("VNPAY Callback validation failed: Invalid hash");
            return false;
        }

        String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
        String vnp_TransactionNo = request.getParameter("vnp_TransactionNo");
        String vnp_TxnRef = request.getParameter("vnp_TxnRef");
        String vnp_Amount_str = request.getParameter("vnp_Amount");

        System.out.println("DEBUG: VNPAY Callback Params - TxnRef: " + vnp_TxnRef + ", ResponseCode: " + vnp_ResponseCode + ", Amount_str: " + vnp_Amount_str);

        BigDecimal vnp_Amount = null;
        if (vnp_Amount_str != null && !vnp_Amount_str.isEmpty()) {
            try {
                vnp_Amount = new BigDecimal(vnp_Amount_str).divide(BigDecimal.valueOf(100));
            } catch (NumberFormatException e) {
                System.err.println("ERROR: Failed to parse VNPAY amount '" + vnp_Amount_str + "'. Error: " + e.getMessage());
                // Nếu parse thất bại, coi như lỗi và không xử lý tiếp
                return false;
            }
        } else {
            System.err.println("ERROR: VNPAY Amount string is null or empty. Cannot process callback.");
            return false;
        }

        Optional<PaymentEntity> paymentOptional = paymentRepository.findByTransactionRef(vnp_TxnRef);

        if (!paymentOptional.isPresent()) {
            System.err.println("ERROR: VNPAY Callback: Payment not found for transactionRef: " + vnp_TxnRef);
            return false;
        }

        PaymentEntity payment = paymentOptional.get();
        System.out.println("DEBUG: Found PaymentEntity for callback. ID: " + payment.getId() + ", Current Status: " + payment.getPaymentStatus());

        if (payment.getPaymentStatus() != PaymentStatus.PENDING) {
            System.err.println("WARNING: VNPAY Callback: Payment already processed for transactionRef: " + vnp_TxnRef + " with status: " + payment.getPaymentStatus() + ". Ignoring.");
            return true; // Trả về true để VNPAY không gửi lại callback
        }

        if ("00".equals(vnp_ResponseCode)) {
            System.out.println("DEBUG: VNPAY transaction successful. Updating payment and wallet.");
            payment.setPaymentStatus(PaymentStatus.SUCCESSED);
            payment.setVnpayTransactionNo(vnp_TransactionNo);
            payment.setPaymentTime(Timestamp.valueOf(LocalDateTime.now()));
            payment.setDescription("VNPAY success: " + request.getParameter("vnp_OrderInfo"));

            Long userId = extractUserIdFromOrderInfo(request.getParameter("vnp_OrderInfo"));
            if (userId == null) {
                System.err.println("ERROR: VNPAY Callback: Could not extract userId from orderInfo: " + request.getParameter("vnp_OrderInfo"));
                payment.setPaymentStatus(PaymentStatus.FAILED);
                paymentRepository.save(payment);
                return false;
            }

            WalletEntity wallet = getOrCreateWallet(userId);
            if (vnp_Amount.compareTo(payment.getAmount()) != 0) {
                System.err.println("ERROR: VNPAY Callback: Amount mismatch. VNPAY: " + vnp_Amount + ", Original: " + payment.getAmount());
                payment.setPaymentStatus(PaymentStatus.FAILED);
                payment.setDescription("VNPAY failed: Amount mismatch. " + request.getParameter("vnp_OrderInfo"));
                paymentRepository.save(payment);
                return false;
            }

            wallet.setBalance(wallet.getBalance().add(vnp_Amount));
            walletRepository.save(wallet);
            System.out.println("DEBUG: Wallet updated for userId: " + userId + ", new balance: " + wallet.getBalance());

            // Tạo bản ghi giao dịch ví
            WalletTransactionEntity walletTransaction = new WalletTransactionEntity();
            walletTransaction.setWalletEntity(wallet);
            walletTransaction.setPaymentEntity(payment);
            walletTransaction.setAmount(vnp_Amount);
            walletTransaction.setTransactionTime(Timestamp.valueOf(LocalDateTime.now())); // Đảm bảo cột này tồn tại và có giá trị
            walletTransaction.setWalletTransactionType(WalletTransactionType.DEPOSIT);
            walletTransaction.setDescription("Nạp tiền thành công qua VNPAY. Mã GD VNPAY: " + vnp_TransactionNo);
            try {
                WalletTransactionEntity savedTxn = walletTransactionRepository.save(walletTransaction);
                System.out.println("DEBUG: WalletTransactionEntity saved with ID: " + savedTxn.getId());
            } catch (Exception e) {
                System.err.println("ERROR: Failed to save WalletTransactionEntity. Error: " + e.getMessage());
                // Xử lý lỗi lưu giao dịch ví, có thể rollback hoặc ghi log cảnh báo
                payment.setPaymentStatus(PaymentStatus.FAILED); // Đặt lại payment status nếu lưu txn thất bại
                paymentRepository.save(payment);
                return false;
            }

            paymentRepository.save(payment); // Lưu cập nhật trạng thái payment cuối cùng
            System.out.println("DEBUG: PaymentEntity final status updated to SUCCESSED.");
            return true;
        } else {
            System.out.println("DEBUG: VNPAY transaction failed. Response Code: " + vnp_ResponseCode);
            payment.setPaymentStatus(PaymentStatus.FAILED);
            payment.setVnpayTransactionNo(vnp_TransactionNo);
            payment.setPaymentTime(Timestamp.valueOf(LocalDateTime.now()));
            payment.setDescription("VNPAY failed. Response Code: " + vnp_ResponseCode + ". " + request.getParameter("vnp_OrderInfo"));
            paymentRepository.save(payment);
            System.out.println("DEBUG: PaymentEntity final status updated to FAILED.");
            return false;
        }
    }

    @Override
    @Transactional
    public boolean withdrawFromWallet(Long userId, BigDecimal amount) {
        System.out.println("DEBUG: withdrawFromWallet called for userId: " + userId + ", amount: " + amount);
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }

        WalletEntity wallet = walletRepository.findByUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found for user: " + userId));

        if (wallet.getBalance().compareTo(amount) < 0) {
            System.out.println("DEBUG: Insufficient balance for withdrawal. Current: " + wallet.getBalance() + ", Requested: " + amount);
            return false; // Số dư không đủ
        }

        wallet.setBalance(wallet.getBalance().subtract(amount));
        walletRepository.save(wallet);
        System.out.println("DEBUG: Wallet balance updated for withdrawal. New balance: " + wallet.getBalance());

        // Tạo bản ghi giao dịch thanh toán (tượng trưng cho giao dịch rút tiền)
        PaymentEntity payment = new PaymentEntity();
        // CẢNH BÁO: orderEntity là NOT NULL trong PaymentEntity của bạn.
        // Nếu bạn chưa sửa PaymentEntity để cho phép orderEntity là NULL,
        // Dòng này sẽ gây lỗi DataIntegrityViolationException.
        payment.setAmount(amount);
        payment.setPaymentTime(Timestamp.valueOf(LocalDateTime.now()));
        payment.setPaymentMethod(PaymentMethod.CASH); // Sử dụng PaymentMethod.CASH vì BANK_TRANSFER không có trong enum bạn cung cấp
        payment.setPaymentStatus(PaymentStatus.SUCCESSED);
        payment.setTransactionRef(UUID.randomUUID().toString());
        payment.setDescription("Rút tiền từ ví về tài khoản ngân hàng (giả định)");
        try {
            PaymentEntity savedPayment = paymentRepository.save(payment); // Dòng này có thể gây lỗi
            System.out.println("DEBUG: PaymentEntity for withdrawal initiated and saved with ID: " + savedPayment.getId() + ", TxnRef: " + savedPayment.getTransactionRef());
        } catch (Exception e) {
            System.err.println("ERROR: Failed to save PaymentEntity during withdrawal. This is likely due to orderEntity NOT NULL constraint. Error: " + e.getMessage());
            throw new RuntimeException("Failed to process withdrawal due to database error. Please check PaymentEntity's orderEntity constraint.", e);
        }


        // Tạo bản ghi giao dịch ví
        WalletTransactionEntity walletTransaction = new WalletTransactionEntity();
        walletTransaction.setWalletEntity(wallet);
        walletTransaction.setPaymentEntity(payment);
        walletTransaction.setAmount(amount);
        walletTransaction.setTransactionTime(Timestamp.valueOf(LocalDateTime.now()));
        walletTransaction.setWalletTransactionType(WalletTransactionType.WITHDRAW);
        walletTransaction.setDescription("Rút tiền từ ví");
        try {
            WalletTransactionEntity savedTxn = walletTransactionRepository.save(walletTransaction);
            System.out.println("DEBUG: WalletTransactionEntity for withdrawal saved with ID: " + savedTxn.getId());
        } catch (Exception e) {
            System.err.println("ERROR: Failed to save WalletTransactionEntity for withdrawal. Error: " + e.getMessage());
            // Xử lý lỗi lưu giao dịch ví, có thể rollback hoặc ghi log cảnh báo
            return false;
        }

        return true;
    }

    /**
     * Helper method để trích xuất userId từ chuỗi orderInfo của VNPAY.
     * Định dạng dự kiến: "Nap tien vao vi cho user {userId}. Ref: {transactionRef}"
     *
     * @param orderInfo Chuỗi thông tin đơn hàng từ VNPAY
     * @return userId hoặc null nếu không thể trích xuất
     */
    private Long extractUserIdFromOrderInfo(String orderInfo) {
        if (orderInfo != null && orderInfo.startsWith("Nap tien vao vi cho user ")) {
            try {
                String userIdPart = orderInfo.substring("Nap tien vao vi cho user ".length());
                int refIndex = userIdPart.indexOf(". Ref:");
                if (refIndex != -1) {
                    userIdPart = userIdPart.substring(0, refIndex);
                }
                return Long.parseLong(userIdPart.trim());
            } catch (NumberFormatException e) {
                System.err.println("Error parsing userId from orderInfo: " + orderInfo + ". Error: " + e.getMessage());
            }
        }
        return null;
    }
}
