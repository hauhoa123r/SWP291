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

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;


    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }


    @Override
    public Page<PaymentResponse> getAllPayments(int page, int size) {
        // Triển khai cho phương thức cũ, nếu bạn vẫn muốn giữ nó
        Pageable pageable = PageRequest.of(page, size);
        Page<PaymentEntity> paymentPage = paymentRepository.findAll(pageable);
        return paymentPage.map(this::convertToResponse);
    }

    @Override
    public Page<PaymentResponse> getAllPayments(Pageable pageable, String searchTerm, String filterStatus) {
        Page<PaymentEntity> paymentPage;

        PaymentStatus statusEnum = null;
        if (filterStatus != null && !filterStatus.equalsIgnoreCase("All") && !filterStatus.isEmpty()) {
            try {
                statusEnum = PaymentStatus.valueOf(filterStatus.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid PaymentStatus provided: " + filterStatus);
            }
        }

        if (searchTerm != null && !searchTerm.trim().isEmpty() && statusEnum != null) {
            paymentPage = paymentRepository.findByOrderEntity_AppointmentEntity_PatientEntity_FullNameContainingIgnoreCaseAndPaymentStatus(searchTerm.trim(), statusEnum, pageable);
        } else if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            paymentPage = paymentRepository.findByOrderEntity_AppointmentEntity_PatientEntity_FullNameContainingIgnoreCase(searchTerm.trim(), pageable);
        } else if (statusEnum != null) {
            paymentPage = paymentRepository.findByPaymentStatus(statusEnum, pageable);
        } else {
            paymentPage = paymentRepository.findAll(pageable);
        }

        return paymentPage.map(this::convertToResponse);
    }


    @Override
    public List<PaymentMethod> getAllPaymentMethods() {
        return Arrays.asList(PaymentMethod.values());
    }

    @Override
    public Optional<PaymentResponse> getPaymentById(Long id) {
        return paymentRepository.findById(id).map(this::convertToResponse);
    }

    @Override
    public PaymentResponse savePayment(PaymentResponse paymentResponse) {
        PaymentEntity paymentEntity;
        if (paymentResponse.getPaymentId() != null) {
            // Update existing payment
            paymentEntity = paymentRepository.findById(paymentResponse.getPaymentId())
                    .orElseThrow(() -> new RuntimeException("Payment not found with ID: " + paymentResponse.getPaymentId()));

            // Cập nhật các trường từ paymentResponse vào paymentEntity
            if (paymentResponse.getAmount() != null) {
                paymentEntity.setAmount(paymentResponse.getAmount());
            }
            if (paymentResponse.getPaymentMethod() != null) {
                paymentEntity.setPaymentMethod(PaymentMethod.valueOf(paymentResponse.getPaymentMethod()));
            }
            if (paymentResponse.getPaymentStatus() != null) {
                paymentEntity.setPaymentStatus(PaymentStatus.valueOf(paymentResponse.getPaymentStatus()));
            }
            // Các trường khác như paymentTime, orderEntity, transactionRef, vnpayTransactionNo
            // có thể cần logic cập nhật riêng tùy thuộc vào nghiệp vụ của bạn.
            // Ví dụ: paymentEntity.setPaymentTime(Timestamp.valueOf(paymentResponse.getPaymentTime()));
            // Nếu bạn muốn cập nhật orderEntity, bạn cần fetch OrderEntity từ DB và set vào đây.

        } else {
            // Create new payment
            paymentEntity = new PaymentEntity();
            paymentEntity.setAmount(paymentResponse.getAmount());
            paymentEntity.setPaymentMethod(PaymentMethod.valueOf(paymentResponse.getPaymentMethod()));
            paymentEntity.setPaymentStatus(PaymentStatus.valueOf(paymentResponse.getPaymentStatus()));
            paymentEntity.setPaymentTime(Timestamp.valueOf(LocalDateTime.now())); // Set current time for new payments
            // Nếu orderCode được cung cấp trong PaymentResponse, bạn có thể cần fetch/set OrderEntity ở đây
            // Ví dụ: paymentEntity.setOrderEntity(orderRepository.findByOrderCode(paymentResponse.getOrderCode()).orElse(null));
        }
        PaymentEntity savedPayment = paymentRepository.save(paymentEntity);
        return convertToResponse(savedPayment);
    }

    @Override
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }


    private PaymentResponse convertToResponse(PaymentEntity payment) {
        PaymentResponse response = new PaymentResponse();
        response.setPaymentId(payment.getId());
        response.setAmount(payment.getAmount());
        response.setPaymentMethod(payment.getPaymentMethod().name());
        response.setPaymentStatus(payment.getPaymentStatus().name());
        response.setPaymentTime(payment.getPaymentTime() != null ? payment.getPaymentTime().toString() : null);

        if (payment.getOrderEntity() != null &&
                payment.getOrderEntity().getAppointmentEntity() != null &&
                payment.getOrderEntity().getAppointmentEntity().getPatientEntity() != null) {
            response.setFullName(payment.getOrderEntity().getAppointmentEntity().getPatientEntity().getFullName());
            
        } else {
            response.setFullName("N/A");
            response.setOrderCode("N/A");
        }

        return response;
    }


    @Override
    public PaymentResponse executePaymentTransaction(PaymentRequest paymentRequest) {
        PaymentEntity payment = new PaymentEntity();
        payment.setAmount(paymentRequest.getAmount());
        payment.setPaymentMethod(PaymentMethod.valueOf(paymentRequest.getPaymentMethod()));
        payment.setPaymentStatus(PaymentStatus.SUCCESSED);
        payment.setPaymentTime(Timestamp.valueOf(LocalDateTime.now()));
        paymentRepository.save(payment);

        return convertToResponse(payment);
    }
}
