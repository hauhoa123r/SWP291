package org.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "payments", schema = "swp391")
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity orderEntity;

    @NotNull
    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "payment_time")
    private Timestamp paymentTime;

    @OneToMany
    private Set<WalletTransactionEntity> walletTransactionEntities = new LinkedHashSet<>();

/*
 TODO [Reverse Engineering] create field to map the 'payment_method' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @Column(name = "payment_method", columnDefinition = "enum not null")
    private Object paymentMethod;
*/
/*
 TODO [Reverse Engineering] create field to map the 'payment_status' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @Column(name = "payment_status", columnDefinition = "enum not null")
    private Object paymentStatus;
*/
}