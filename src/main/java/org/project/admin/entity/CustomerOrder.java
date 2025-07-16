package org.project.admin.entity;//package com.example.kivicarebackend.entity;
//
//import org.project.admin.enums.order.OrderStatus;
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Entity
//@Table(name = "customer_orders")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class CustomerOrder {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "customer_order_id")
//    private Long customerOrderId;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @ManyToOne
//    @JoinColumn(name = "shipping_address_id")
//    private ShippingAddress shippingAddress;
//
//    @Column(name = "shipping_fee")
//    private BigDecimal shippingFee;
//
//    @Column(name = "total_amount")
//    private BigDecimal totalAmount;
//
//    @Column(name = "real_amount")
//    private BigDecimal realAmount;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "order_status")
//    private OrderStatus orderStatus;
//
//    @ManyToOne
//    @JoinColumn(name = "coupon_id")
//    private Coupon coupon;
//
//    @Column(name = "order_date")
//    private LocalDateTime orderDate;
//
//    @OneToMany(mappedBy = "customerOrder", cascade = CascadeType.ALL)
//    private List<CustomerOrderItem> orderItems;
//}
//
