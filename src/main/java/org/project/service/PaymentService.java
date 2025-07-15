package org.project.service;

import org.project.enums.PaymentMethod;
import org.project.model.request.PaymentRequest;
import org.project.model.response.PaymentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PaymentService {


    PaymentResponse executePaymentTransaction(PaymentRequest paymentRequest);

    // Phương thức getAllPayments cũ (giữ lại)
    Page<PaymentResponse> getAllPayments(int page, int size);

    // Phương thức getAllPayments mới, hỗ trợ phân trang, tìm kiếm và lọc
    Page<PaymentResponse> getAllPayments(Pageable pageable, String searchTerm, String filterStatus);

    List<PaymentMethod> getAllPaymentMethods();

    Optional<PaymentResponse> getPaymentById(Long id);

    PaymentResponse savePayment(PaymentResponse paymentResponse);

    void deletePayment(Long id);
}
