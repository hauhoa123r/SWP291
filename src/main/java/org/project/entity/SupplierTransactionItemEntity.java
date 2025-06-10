package org.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "supplier_transaction_items", schema = "swp391")
public class SupplierTransactionItemEntity {
    @EmbeddedId
    private SupplierTransactionItemEntityId id;

    @MapsId("supplierTransactionId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "supplier_transaction_id", nullable = false)
    private SupplierTransactionsEntity supplierTransactionEntity;

    @MapsId("productId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity productEntity;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Size(max = 255)
    @NotNull
    @ColumnDefault("'VND'")
    @Column(name = "currency_unit", nullable = false)
    private String currencyUnit;

    @NotNull
    @Column(name = "manufacture_date", nullable = false)
    private Date manufactureDate;

    @NotNull
    @Column(name = "expiration_date", nullable = false)
    private Date expirationDate;

}