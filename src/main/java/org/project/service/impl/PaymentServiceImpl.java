package org.project.service.impl;

import org.project.entity.PaymentEntity;
import org.project.enums.PaymentMethod;
import org.project.enums.PaymentStatus;
import org.project.model.request.PaymentRequest;
import org.project.model.response.PaymentResponse;
import org.project.repository.PaymentRepository;
import org.project.service.PaymentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;


    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
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


    @Override
    public PaymentResponse executePaymentTransaction(PaymentRequest paymentRequest) {
        PaymentEntity payment = new PaymentEntity();
        payment.setAmount(paymentRequest.getAmount());
        payment.setPaymentMethod(PaymentMethod.valueOf(paymentRequest.getPaymentMethod()));
        payment.setPaymentStatus(PaymentStatus.SUCCESSED);
        paymentRepository.save(payment);

        return convertToResponse(payment);
    }


}
