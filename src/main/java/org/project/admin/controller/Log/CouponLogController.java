package org.project.admin.controller.Log;

import org.project.admin.dto.request.LogSearchRequest;
import org.project.admin.entity.Log.CouponLog;
import org.project.admin.service.Log.CouponLogService;
import org.project.admin.util.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/coupon/logs")
@RequiredArgsConstructor
public class CouponLogController {
    private final CouponLogService couponLogService;

    @GetMapping
    public PageResponse<CouponLog> getAllLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return couponLogService.getAllLogs(page, size);
    }

    @GetMapping("/coupon/{couponId}")
    public List<CouponLog> getLogsByCoupon(@PathVariable Long couponId) {
        return couponLogService.getLogsByCoupon(couponId);
    }

    @PostMapping("/search")
    public PageResponse<CouponLog> searchLogs(
            @RequestBody LogSearchRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return couponLogService.searchLogs(request, page, size);
    }
}
