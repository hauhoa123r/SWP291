package org.project.service.impl;

import org.project.entity.*;
import org.project.enums.PaymentMethod;
import org.project.enums.PaymentStatus;
import org.project.enums.WalletTransactionType;
import org.project.model.request.PaymentRequest;
import org.project.model.response.PaymentResponse;
import org.project.repository.PaymentRepository;
import org.project.repository.PrescriptionRepository;
import org.project.repository.WalletRepository;
import org.project.repository.WalletTransactionRepository;
import org.project.service.PaymentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    private final PrescriptionRepository prescriptionRepository;

    private final WalletRepository walletRepository;

    private final WalletTransactionRepository walletTransactionRepository;

    public PaymentServiceImpl(PrescriptionRepository prescriptionRepository, PaymentRepository paymentRepository, WalletRepository walletRepository, WalletTransactionRepository walletTransactionRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.paymentRepository = paymentRepository;
        this.walletRepository = walletRepository;
        this.walletTransactionRepository = walletTransactionRepository;
    }

    public BigDecimal calculateTotalAmount(Long prescriptionId) {
        PrescriptionEntity prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new RuntimeException("Prescription not found"));
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (PrescriptionItemEntity item : prescription.getPrescriptionItemEntities()) {
            BigDecimal itemTotal = item.getMedicineEntity().getProductEntity().getPrice().multiply(new BigDecimal(item.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);
        }
        return totalAmount;
    }

    // Kiểm tra số dư ví của người dùng
    public WalletEntity checkWalletBalance(Long userId, BigDecimal totalAmount) {
        WalletEntity wallet = walletRepository.findByUserEntityId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
        if (wallet.getBalance().compareTo(totalAmount) < 0) {
            throw new RuntimeException("Insufficient balance in wallet");
        }
        return wallet;
    }


    // Tạo giao dịch thanh toán
    public PaymentEntity createPayment(BigDecimal totalAmount, PaymentMethod paymentMethod) {
        PaymentEntity payment = new PaymentEntity();
        payment.setAmount(totalAmount);
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setPaymentTime(new Timestamp(System.currentTimeMillis()));
        return paymentRepository.save(payment);
    }

    // Cập nhật số dư ví người dùng
    public void updateWalletBalance(WalletEntity wallet, BigDecimal totalAmount) {
        wallet.setBalance(wallet.getBalance().subtract(totalAmount));
        walletRepository.save(wallet);
    }

    // Lưu thông tin giao dịch ví
    public void saveWalletTransaction(WalletEntity wallet, PaymentEntity payment, BigDecimal totalAmount, Long prescriptionId) {
        WalletTransactionEntity transaction = new WalletTransactionEntity();
        transaction.setWalletEntity(wallet);
        transaction.setPaymentEntity(payment);
        transaction.setAmount(totalAmount);
        transaction.setDescription("Payment for prescription #" + prescriptionId);
        transaction.setWalletTransactionType(WalletTransactionType.DEBIT);
        walletTransactionRepository.save(transaction);
    }

    // Cập nhật trạng thái thanh toán thành công
    public void finalizePayment(PaymentEntity payment) {
        payment.setPaymentStatus(PaymentStatus.SUCCESSED);
        paymentRepository.save(payment);
    }

    // Phương thức thanh toán chính
    @Override
    public PaymentEntity processPayment(Long prescriptionId, Long userId, PaymentMethod paymentMethod) {
        BigDecimal totalAmount = calculateTotalAmount(prescriptionId);
        WalletEntity wallet = checkWalletBalance(userId, totalAmount);
        PaymentEntity payment = createPayment(totalAmount, paymentMethod);
        updateWalletBalance(wallet, totalAmount);
        saveWalletTransaction(wallet, payment, totalAmount, prescriptionId);
        finalizePayment(payment);
        return payment;
    }

    @Override
    public PaymentResponse executePaymentTransaction(PaymentRequest paymentRequest) {
        PaymentEntity payment = new PaymentEntity();
        payment.setAmount(paymentRequest.getAmount());
        payment.setPaymentMethod(PaymentMethod.valueOf(paymentRequest.getPaymentMethod()));
        payment.setPaymentStatus(PaymentStatus.SUCCESSED);
        paymentRepository.save(payment);

        return convertToResponse(payment);
    }

    @Override
    public Page<PaymentResponse> getAllPayments(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PaymentEntity> paymentPage = paymentRepository.findAll(pageable);
        return paymentPage.map(this::convertToResponse);
    }

    @Override
    public List<PaymentMethod> getAllPaymentMethods() {
        return Arrays.asList(PaymentMethod.values());
    }


//    @Override
//    public PaymentResponse updatePaymentMethod(Long paymentId, PaymentMethod newMethod) {
//        PaymentEntity payment = paymentRepository.findById(paymentId)
//                .orElseThrow(() -> new RuntimeException("Payment not found"));
//        payment.setPaymentMethod(newMethod);
//        paymentRepository.save(payment);
//        return convertToResponse(payment);
//    }

    private PaymentResponse convertToResponse(PaymentEntity payment) {
        PaymentResponse response = new PaymentResponse();
        response.setPaymentId(payment.getId());
        response.setAmount(payment.getAmount());
        response.setPaymentMethod(payment.getPaymentMethod().name());
        response.setPaymentStatus(payment.getPaymentStatus().name());
        response.setPaymentTime(payment.getPaymentTime() != null ? payment.getPaymentTime().toString() : null);


        if (payment.getOrderEntity().getAppointmentEntity().getPatientEntity() != null) {
            response.setFullName(payment.getOrderEntity().getAppointmentEntity().getPatientEntity().getFullName());
        }

        return response;
    }

}
