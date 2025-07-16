package org.project.admin.controller;

import org.project.admin.dto.request.CouponProductRequest;
import org.project.admin.dto.response.CouponProductResponse;
import org.project.admin.service.CouponProductService;
import org.project.admin.util.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/coupon-products")
@RequiredArgsConstructor
public class CouponProductAdminController {

    private final CouponProductService couponProductService;

    @PostMapping("/{couponId}/products")
    public void assignProducts(
            @PathVariable Long couponId,
            @RequestBody List<Long> productIds
    ) {
        CouponProductRequest request = new CouponProductRequest();
        request.setCouponId(couponId);
        request.setProductIds(productIds);
        couponProductService.assignProductsToCoupon(request);
    }

    @DeleteMapping("/{couponId}/products/{productId}")
    public void removeProductFromCoupon(
            @PathVariable Long couponId,
            @PathVariable Long productId
    ) {
        couponProductService.removeProductFromCoupon(couponId, productId);
    }

    @GetMapping("/{couponId}/products")
    public List<CouponProductResponse> getProductsByCoupon(
            @PathVariable Long couponId
    ) {
        return couponProductService.getProductsByCoupon(couponId);
    }

    @GetMapping("/{couponId}/products/paging")
    public PageResponse<CouponProductResponse> getProductsByCouponPaging(
            @PathVariable Long couponId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return couponProductService.getProductsByCoupon(couponId, page, size);
    }
}
