package org.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity {
    @Column(name = "payment_id")
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "method")
    private String method;

    @Column(name = "status")
    private String status;

    @Column(name = "payment_time")
    private Date paymentDate;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private PrescriptionOrderEntity prescriptionOrderEntity;
}
