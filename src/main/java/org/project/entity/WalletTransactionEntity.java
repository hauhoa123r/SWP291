// src/main/java/org/project/entity/WalletTransactionEntity.java
package org.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.enums.WalletTransactionType;

import java.math.BigDecimal;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity// Hoặc @Entity
@Table(name = "wallet_transactions", schema = "swp391") // Đảm bảo schema của bạn
public class WalletTransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wallet_transaction_id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wallet_id", nullable = false)
    private WalletEntity walletEntity;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "payment_id", nullable = false)
    private PaymentEntity paymentEntity; // Liên kết với PaymentEntity

    @NotNull
    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Size(max = 255)
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "transaction_type", nullable = false)
    @Enumerated(EnumType.STRING) // Lưu tên enum dưới dạng String
    private WalletTransactionType walletTransactionType;

    @NotNull
    @Column(name = "transaction_time", nullable = false)
    private Timestamp transactionTime; // Đảm bảo trường này có và có setter

    // Không cần setters/getters thủ công nếu đã dùng Lombok @Getter @Setter
}