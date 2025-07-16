package org.project.admin.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class CouponProductRequest {
    private Long couponId;
    private List<Long> productIds;
}
