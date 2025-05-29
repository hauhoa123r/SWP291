package org.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Date;

@Entity
@Table(name = "supplier_transactions")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SupplierTransactionEntity {

    @Column(name = "transaction_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "transaction_date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private SupplierEntity supplierEntity;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private PharmacyStaffEntity pharmacyStaffEntity;

    @OneToMany(mappedBy = "supplierTransactionEntity",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TransactionDetailEntity> transactionDetailEntity;
}
