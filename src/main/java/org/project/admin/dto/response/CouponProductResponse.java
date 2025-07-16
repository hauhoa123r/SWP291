package org.project.admin.dto.response;

import lombok.Data;

@Data
public class CouponProductResponse {
    private Long id;
    private Long couponId;
    private String couponCode;
    private Long productId;
    private String productName;
}
