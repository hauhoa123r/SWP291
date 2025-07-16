package org.project.admin.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.project.admin.enums.coupon.CouponStatus;
import org.project.admin.enums.coupon.DiscountType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "coupons")
@Getter
@Setter
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long couponId;

    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", nullable = false)
    private DiscountType discountType; // PERCENTAGE, FIXED

    @Column(name = "value", nullable = false)
    private BigDecimal value; // Giá trị giảm

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CouponStatus status = CouponStatus.ACTIVE;

    @Column(name = "usage_limit")
    private Integer usageLimit; // Tổng số lượt được sử dụng

    @Column(name = "used_count")
    private Integer usedCount = 0; // Đã sử dụng bao nhiêu lượt

    @Column(name = "user_usage_limit")
    private Integer userUsageLimit; // Mỗi user tối đa được dùng mấy lượt

    @Column(name = "min_order_amount")
    private BigDecimal minOrderAmount;
}
