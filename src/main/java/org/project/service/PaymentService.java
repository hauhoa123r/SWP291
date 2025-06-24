package org.project.service;

import org.project.entity.PaymentEntity;
import org.project.entity.WalletEntity;
import org.project.enums.PaymentMethod;
import org.project.model.request.PaymentRequest;
import org.project.model.response.PaymentResponse;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentService {

    PaymentEntity processPayment(Long prescriptionId, Long userId, PaymentMethod paymentMethod);


    BigDecimal calculateTotalAmount(Long prescriptionId);

    WalletEntity checkWalletBalance(Long userId, BigDecimal totalAmount);

    PaymentEntity createPayment(BigDecimal totalAmount, PaymentMethod paymentMethod);

    void updateWalletBalance(WalletEntity wallet, BigDecimal totalAmount);

    void saveWalletTransaction(WalletEntity wallet, PaymentEntity payment, BigDecimal totalAmount, Long prescriptionId);

    void finalizePayment(PaymentEntity payment);

    PaymentResponse executePaymentTransaction(PaymentRequest paymentRequest);


    Page<PaymentResponse> getAllPayments(int page, int size);

    List<PaymentMethod> getAllPaymentMethods();

//    PaymentResponse updatePaymentMethod(Long paymentId, String newMethod);
}
