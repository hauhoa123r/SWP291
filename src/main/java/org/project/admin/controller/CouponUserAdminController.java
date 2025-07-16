package org.project.admin.controller;

import org.project.admin.dto.request.CouponUserRequest;
import org.project.admin.dto.response.CouponUserResponse;
import org.project.admin.service.CouponUserService;
import org.project.admin.util.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/coupon-users")
@RequiredArgsConstructor
public class CouponUserAdminController {

    private final CouponUserService couponUserService;

    @PostMapping("/{couponId}/users")
    public void assignUsers(
            @PathVariable Long couponId,
            @RequestBody List<Long> userIds
    ) {
        CouponUserRequest request = new CouponUserRequest();
        request.setCouponId(couponId);
        request.setUserIds(userIds);
        couponUserService.assignUsersToCoupon(request);
    }

    @DeleteMapping("/{couponId}/users/{userId}")
    public void removeUserFromCoupon(
            @PathVariable Long couponId,
            @PathVariable Long userId
    ) {
        couponUserService.removeUserFromCoupon(couponId, userId);
    }

    @GetMapping("/{couponId}/users")
    public List<CouponUserResponse> getUsersByCoupon(
            @PathVariable Long couponId
    ) {
        return couponUserService.getUsersByCoupon(couponId);
    }

    @GetMapping("/{couponId}/users/paging")
    public PageResponse<CouponUserResponse> getUsersByCouponPaging(
            @PathVariable Long couponId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return couponUserService.getUsersByCoupon(couponId, page, size);
    }
}
