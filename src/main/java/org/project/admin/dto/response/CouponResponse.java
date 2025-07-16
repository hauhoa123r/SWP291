package org.project.admin.dto.response;

import org.project.admin.enums.coupon.CouponStatus;
import org.project.admin.enums.coupon.DiscountType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CouponResponse {
    private Long couponId;
    private String code;
    private String description;
    private DiscountType discountType;
    private BigDecimal value;
    private LocalDate startDate;
    private LocalDate expirationDate;
    private Integer usageLimit;
    private Integer userUsageLimit;
    private Integer usedCount;
    private BigDecimal minOrderAmount;
    private CouponStatus status;
}
