package org.project.admin.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.project.admin.enums.order.OrderStatus;
import org.project.admin.enums.order.OrderType;

import java.math.BigDecimal;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_type")
    private OrderType orderType;

    @ManyToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "shipping_address_id")
    private ShippingAddress shippingAddress;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @Column(name = "real_amount")
    private BigDecimal realAmount;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;


}

