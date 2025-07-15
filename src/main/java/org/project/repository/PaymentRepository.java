package org.project.repository;

import org.project.entity.PaymentEntity;
import org.project.enums.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    Page<PaymentEntity> findAll(Pageable pageable);

    Optional<PaymentEntity> findByTransactionRef(String transactionRef);

    Page<PaymentEntity> findByOrderEntity_AppointmentEntity_PatientEntity_FullNameContainingIgnoreCase(String fullName, Pageable pageable);

    Page<PaymentEntity> findByPaymentStatus(PaymentStatus paymentStatus, Pageable pageable);

    Page<PaymentEntity> findByOrderEntity_AppointmentEntity_PatientEntity_FullNameContainingIgnoreCaseAndPaymentStatus(String fullName, PaymentStatus paymentStatus, Pageable pageable);
}
