package org.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "prescription_details")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionDetailEntity {

    @Column(name = "detail_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dosage")
    private String dosage;

    @Column(name = "duration_days")
    private Long durationDays;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private PharmacyProductEntity pharmacyProductEntity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private PrescriptionOrderEntity prescriptionOrderEntity;
}
