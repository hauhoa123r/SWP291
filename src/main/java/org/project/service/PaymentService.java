package org.project.service;

import org.project.entity.PaymentEntity;
import org.project.entity.WalletEntity;
import org.project.enums.PaymentMethod;

import java.math.BigDecimal;

public interface PaymentService {

    PaymentEntity processPayment(Long prescriptionId, Long userId, PaymentMethod paymentMethod);


    BigDecimal calculateTotalAmount(Long prescriptionId);

    WalletEntity checkWalletBalance(Long userId, BigDecimal totalAmount);

    PaymentEntity createPayment(BigDecimal totalAmount, PaymentMethod paymentMethod);

    void updateWalletBalance(WalletEntity wallet, BigDecimal totalAmount);

    void saveWalletTransaction(WalletEntity wallet, PaymentEntity payment, BigDecimal totalAmount, Long prescriptionId);

    void finalizePayment(PaymentEntity payment);
}
