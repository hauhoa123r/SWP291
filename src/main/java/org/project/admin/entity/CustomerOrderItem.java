package org.project.admin.entity;//package com.example.kivicarebackend.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//@Entity
//@Table(name = "customer_order_items")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class CustomerOrderItem {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "customer_order_item_id")
//    private Long customerOrderItemId;
//
//    @ManyToOne
//    @JoinColumn(name = "customer_order_id")
//    private CustomerOrder customerOrder;
//
//    @ManyToOne
//    @JoinColumn(name = "product_id")
//    private Product product; // Entity Product
//
//    @Column(name = "quantity")
//    private Integer quantity;
//}
