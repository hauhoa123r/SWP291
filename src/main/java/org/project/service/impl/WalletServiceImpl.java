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
        return walletRepository.findByUser_Id(userId).orElseGet(() -> {
            UserEntity user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
            WalletEntity newWallet = new WalletEntity();
            newWallet.setUser(user);
            newWallet.setBalance(BigDecimal.ZERO);
            return walletRepository.save(newWallet);
        });
    }

    @Override
    @Transactional
    public String initiateVnpayDeposit(Long userId, BigDecimal amount, HttpServletRequest request) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String transactionRef = UUID.randomUUID().toString();
        String orderInfo = "Nap tien vao vi cho user " + userId + ". Ref: " + transactionRef;

        PaymentEntity payment = new PaymentEntity();
        // Cảnh báo: orderEntity là NOT NULL trong PaymentEntity của bạn.
        // Việc không gán OrderEntity ở đây sẽ gây lỗi khi lưu vào DB.
        // Bạn cần đảm bảo đã sửa PaymentEntity hoặc cung cấp một OrderEntity giả.
        payment.setAmount(amount);
        payment.setPaymentTime(Timestamp.valueOf(LocalDateTime.now()));
        payment.setPaymentMethod(PaymentMethod.VNPAY);
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setTransactionRef(transactionRef);
        payment.setDescription(orderInfo);
        paymentRepository.save(payment); // Dòng này sẽ gây lỗi nếu order_id là NOT NULL và bạn không gán

        return vnpayService.createPaymentUrl(transactionRef, orderInfo, amount, request);
    }

    @Override
    @Transactional
    public boolean handleVnpayCallback(HttpServletRequest request) {
        if (!vnpayService.validateVnpayCallback(request)) {
            System.err.println("VNPAY Callback validation failed: Invalid hash");
            return false;
        }

        String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
        String vnp_TransactionNo = request.getParameter("vnp_TransactionNo");
        String vnp_TxnRef = request.getParameter("vnp_TxnRef");
        BigDecimal vnp_Amount = new BigDecimal(request.getParameter("vnp_Amount")).divide(BigDecimal.valueOf(100));
        String vnp_OrderInfo = request.getParameter("vnp_OrderInfo");

        Optional<PaymentEntity> paymentOptional = paymentRepository.findByTransactionRef(vnp_TxnRef);

        if (!paymentOptional.isPresent()) {
            System.err.println("VNPAY Callback: Payment not found for transactionRef: " + vnp_TxnRef);
            return false;
        }

        PaymentEntity payment = paymentOptional.get();

        if (payment.getPaymentStatus() != PaymentStatus.PENDING) {
            System.err.println("VNPAY Callback: Payment already processed for transactionRef: " + vnp_TxnRef + " with status: " + payment.getPaymentStatus());
            return true; // Trả về true để VNPAY không gửi lại callback
        }

        if ("00".equals(vnp_ResponseCode)) {
            payment.setPaymentStatus(PaymentStatus.SUCCESSED);
            payment.setVnpayTransactionNo(vnp_TransactionNo);
            payment.setPaymentTime(Timestamp.valueOf(LocalDateTime.now()));
            payment.setDescription("VNPAY success: " + vnp_OrderInfo);

            Long userId = extractUserIdFromOrderInfo(vnp_OrderInfo);
            if (userId == null) {
                System.err.println("VNPAY Callback: Could not extract userId from orderInfo: " + vnp_OrderInfo);
                payment.setPaymentStatus(PaymentStatus.FAILED);
                paymentRepository.save(payment);
                return false;
            }

            WalletEntity wallet = getOrCreateWallet(userId);
            if (vnp_Amount.compareTo(payment.getAmount()) != 0) {
                System.err.println("VNPAY Callback: Amount mismatch. VNPAY: " + vnp_Amount + ", Original: " + payment.getAmount());
                payment.setPaymentStatus(PaymentStatus.FAILED);
                payment.setDescription("VNPAY failed: Amount mismatch. " + vnp_OrderInfo);
                paymentRepository.save(payment);
                return false;
            }

            wallet.setBalance(wallet.getBalance().add(vnp_Amount));
            walletRepository.save(wallet);

            WalletTransactionEntity walletTransaction = new WalletTransactionEntity();
            walletTransaction.setWalletEntity(wallet);
            walletTransaction.setPaymentEntity(payment);
            walletTransaction.setAmount(vnp_Amount);
            walletTransaction.setTransactionTime(Timestamp.valueOf(LocalDateTime.now()));
            walletTransaction.setWalletTransactionType(WalletTransactionType.DEPOSIT);
            walletTransaction.setDescription("Nạp tiền thành công qua VNPAY. Mã GD VNPAY: " + vnp_TransactionNo);
            walletTransactionRepository.save(walletTransaction);

            paymentRepository.save(payment);
            return true;
        } else {
            payment.setPaymentStatus(PaymentStatus.FAILED);
            payment.setVnpayTransactionNo(vnp_TransactionNo);
            payment.setPaymentTime(Timestamp.valueOf(LocalDateTime.now()));
            payment.setDescription("VNPAY failed. Response Code: " + vnp_ResponseCode + ". " + vnp_OrderInfo);
            paymentRepository.save(payment);
            return false;
        }
    }

    @Override
    @Transactional
    public boolean withdrawFromWallet(Long userId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }

        WalletEntity wallet = walletRepository.findByUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found for user: " + userId));

        if (wallet.getBalance().compareTo(amount) < 0) {
            return false; // Số dư không đủ
        }

        wallet.setBalance(wallet.getBalance().subtract(amount));
        walletRepository.save(wallet);

        PaymentEntity payment = new PaymentEntity();
        // Cảnh báo: orderEntity là NOT NULL trong PaymentEntity của bạn.
        // Việc không gán OrderEntity ở đây sẽ gây lỗi khi lưu vào DB.
        // Bạn cần đảm bảo đã sửa PaymentEntity hoặc cung cấp một OrderEntity giả.
        payment.setAmount(amount);
        payment.setPaymentTime(Timestamp.valueOf(LocalDateTime.now()));
        payment.setPaymentMethod(PaymentMethod.CASH); // Sử dụng PaymentMethod.CASH vì BANK_TRANSFER không có trong enum bạn cung cấp
        payment.setPaymentStatus(PaymentStatus.SUCCESSED);
        payment.setTransactionRef(UUID.randomUUID().toString());
        payment.setDescription("Rút tiền từ ví về tài khoản ngân hàng (giả định)");
        paymentRepository.save(payment); // Dòng này sẽ gây lỗi nếu order_id là NOT NULL và bạn không gán

        WalletTransactionEntity walletTransaction = new WalletTransactionEntity();
        walletTransaction.setWalletEntity(wallet);
        walletTransaction.setPaymentEntity(payment);
        walletTransaction.setAmount(amount);
        walletTransaction.setTransactionTime(Timestamp.valueOf(LocalDateTime.now()));
        walletTransaction.setWalletTransactionType(WalletTransactionType.WITHDRAW);
        walletTransaction.setDescription("Rút tiền từ ví");
        walletTransactionRepository.save(walletTransaction);

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
