package org.project.service;

import org.project.enums.PaymentMethod;
import org.project.model.request.PaymentRequest;
import org.project.model.response.PaymentResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PaymentService {


    PaymentResponse executePaymentTransaction(PaymentRequest paymentRequest);


    Page<PaymentResponse> getAllPayments(int page, int size);

    List<PaymentMethod> getAllPaymentMethods();


}
