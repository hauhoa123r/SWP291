package org.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "transaction_details")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDetailEntity {

    @Column(name = "detail_id")
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private SupplierTransactionEntity supplierTransactionEntity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private PharmacyProductEntity pharmacyProductEntity;
}
