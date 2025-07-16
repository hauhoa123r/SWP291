package org.project.admin.dto.request;

import org.project.admin.enums.coupon.CouponStatus;
import org.project.admin.enums.coupon.DiscountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
public class CouponRequest {
    @NotBlank
    private String code;

    @NotBlank
    private String description;

    @NotNull
    private DiscountType discountType;

    @NotNull
    private BigDecimal value;

    @NotNull
    private LocalDate expirationDate;

    private LocalDate startDate;
    private BigDecimal minOrderAmount;
    private Integer usageLimit;
    private Integer userUsageLimit;
    private Integer usedCount;

    private CouponStatus status;
}

