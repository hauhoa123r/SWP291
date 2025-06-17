package org.project.service.impl;

import org.project.entity.*;
import org.project.enums.PaymentMethod;
import org.project.enums.PaymentStatus;
import org.project.enums.WalletTransactionType;
import org.project.repository.*;
import org.project.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private WalletTransactionRepository walletTransactionRepository;

    public BigDecimal calculateTotalAmount(Long prescriptionId) {
        PrescriptionEntity prescription = prescriptionRepository.findById(prescriptionId)
                .orElseThrow(() -> new RuntimeException("Prescription not found"));
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (PrescriptionItemEntity item : prescription.getPrescriptionItems()) {
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
}
