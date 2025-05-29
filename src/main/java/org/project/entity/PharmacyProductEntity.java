package org.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;

@Entity
@Table(name = "pharmacy_products")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PharmacyProductEntity {

    @Column(name = "product_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name", nullable = false)
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "unit", nullable = false)
    private String unit;

    @OneToMany(mappedBy = "pharmacyProductEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ArrayList<PharmacyInventoryEntity> pharmacyInventoryEntities;

    @OneToMany(mappedBy = "pharmacyProductEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ArrayList<InventoryBatchEntity> inventoryBatchEntities;

    @OneToMany(mappedBy = "pharmacyProductEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ArrayList<TransactionDetailEntity> transactionDetailEntity;

    @OneToMany(mappedBy = "pharmacyProductEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ArrayList<SubscribedPackageEntity> subscribedPackageEntities;

    @OneToMany(mappedBy = "pharmacyProductEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ArrayList<PrescriptionDetailEntity> prescriptionDetailEntities;

    @OneToMany(mappedBy = "pharmacyProductEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ArrayList<ProductFeedbackEntity> productFeedbackEntity;
}
