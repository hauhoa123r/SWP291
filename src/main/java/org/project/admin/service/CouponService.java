package org.project.admin.service;

import org.project.admin.dto.request.CouponRequest;
import org.project.admin.dto.request.CouponSearchRequest;
import org.project.admin.dto.response.CouponResponse;
import org.project.admin.util.PageResponse;

import java.util.List;
public interface CouponService {
    List<CouponResponse> getAllCoupons();
    CouponResponse getCouponById(Long couponId);
    CouponResponse createCoupon(CouponRequest couponRequest);
    CouponResponse updateCoupon(Long couponId, CouponRequest couponRequest);
    void deleteCoupon(Long couponId);

    PageResponse<CouponResponse> getCouponsWithPagination(int page, int size);
    PageResponse<CouponResponse> searchCoupons(CouponSearchRequest req, int page, int size);
}
