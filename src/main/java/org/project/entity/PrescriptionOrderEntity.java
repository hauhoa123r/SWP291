package org.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "prescription_orders")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionOrderEntity {

    @Column(name = "order_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @Column(name = "status", nullable = false)
    private String status;

    @OneToMany(mappedBy = "prescriptionOrderEntity", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<PrescriptionDetailEntity> prescriptionDetailEntity;

    @OneToMany(mappedBy = "prescriptionOrderEntity", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<PaymentEntity> paymentEntities;
}
