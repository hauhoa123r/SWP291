// src/main/java/org/project/entity/PaymentEntity.java
package org.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.enums.PaymentMethod;
import org.project.enums.PaymentStatus;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "PaymentEntityEntity")
@Table(name = "payments", schema = "swp391") // Đảm bảo schema của bạn
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", nullable = false)
    private Long id;

    // ĐÃ SỬA ĐỔI: Cho phép orderEntity là NULL
    // Bỏ @NotNull và optional = false, bỏ nullable = false trong @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id") // Bỏ nullable = false để cho phép null
    private OrderEntity orderEntity;

    @NotNull
    @Column(name = "amount", nullable = false, precision = 19, scale = 0) // Đã sửa đổi precision/scale
    private BigDecimal amount;

    @Column(name = "payment_time")
    private Timestamp paymentTime;

    @Column(name = "payment_method", nullable = false)
    @Enumerated(EnumType.STRING) // Lưu tên enum dưới dạng String
    private PaymentMethod paymentMethod;

    @Column(name = "payment_status", nullable = false)
    @Enumerated(EnumType.STRING) // Lưu tên enum dưới dạng String
    private PaymentStatus paymentStatus;

    @Size(max = 255)
    @Column(name = "transaction_ref", unique = true) // Mã giao dịch riêng của hệ thống bạn
    private String transactionRef;

    @Size(max = 255)
    @Column(name = "vnpay_transaction_no") // Mã giao dịch của VNPAY
    private String vnpayTransactionNo;

    @Size(max = 255)
    @Column(name = "description") // Mô tả thêm về giao dịch
    private String description;

    @OneToMany(mappedBy = "paymentEntity")
    private Set<WalletTransactionEntity> walletTransactionEntities = new LinkedHashSet<>();
}
