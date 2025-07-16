package org.project.admin.dto.response;

import lombok.Data;

@Data
public class CouponUserResponse {
    private Long id;
    private Long couponId;
    private String couponCode;
    private Long userId;
    private String userEmail;
    private Integer usedCount;
    private String userPhoneNumber;
}
