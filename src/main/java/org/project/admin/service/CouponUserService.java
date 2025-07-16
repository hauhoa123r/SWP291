package org.project.admin.service;

import org.project.admin.dto.request.CouponUserRequest;
import org.project.admin.dto.response.CouponUserResponse;
import org.project.admin.util.PageResponse;

import java.util.List;

public interface CouponUserService {
    void assignUsersToCoupon(CouponUserRequest request);

    void removeUserFromCoupon(Long couponId, Long userId);

    List<CouponUserResponse> getUsersByCoupon(Long couponId);

    PageResponse<CouponUserResponse> getUsersByCoupon(Long couponId, int page, int size);
}
