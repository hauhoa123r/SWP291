package org.project.admin.service;

import org.project.admin.dto.request.CouponProductRequest;
import org.project.admin.dto.response.CouponProductResponse;
import org.project.admin.util.PageResponse;

import java.util.List;

public interface CouponProductService {
    void assignProductsToCoupon(CouponProductRequest request);
    void removeProductFromCoupon(Long couponId, Long productId);
    List<CouponProductResponse> getProductsByCoupon(Long couponId);
    PageResponse<CouponProductResponse> getProductsByCoupon(Long couponId, int page, int size);
}
