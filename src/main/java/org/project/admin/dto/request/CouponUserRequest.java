package org.project.admin.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class CouponUserRequest {
    private Long couponId;
    private List<Long> userIds;
}
