package org.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders", schema = "swp391")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "appointment_id", nullable = false)
    private AppointmentEntity appointmentEntity;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shipping_address_id", nullable = false)
    private ShippingAddressEntity shippingAddressEntity;

    @ColumnDefault("0.00")
    @Column(name = "shipping_fee", precision = 10, scale = 2)
    private BigDecimal shippingFee;

    @NotNull
    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @NotNull
    @Column(name = "real_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal realAmount;
    @OneToMany
    private Set<OrderItemEntity> orderItemEntities = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private CouponEntity couponEntity;
    @OneToMany
    private Set<PaymentEntity> paymentEntities = new LinkedHashSet<>();

/*
 TODO [Reverse Engineering] create field to map the 'order_type' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @ColumnDefault("'DIRECT'")
    @Column(name = "order_type", columnDefinition = "enum not null")
    private Object orderType;
*/
/*
 TODO [Reverse Engineering] create field to map the 'order_status' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @Column(name = "order_status", columnDefinition = "enum not null")
    private Object orderStatus;
*/
}