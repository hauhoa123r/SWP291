package org.project.admin.service.Log;

import org.project.admin.dto.request.LogSearchRequest;
import org.project.admin.dto.response.CouponResponse;
import org.project.admin.entity.Coupon;
import org.project.admin.entity.Log.CouponLog;
import org.project.admin.enums.AuditAction;
import org.project.admin.util.PageResponse;

import java.util.List;

public interface CouponLogService {
    void logCouponAction(Coupon coupon, AuditAction action);
    void logCouponUpdateAction(CouponResponse oldCoupon, CouponResponse newCoupon, AuditAction action);
    List<CouponLog> getLogsByCoupon(Long couponId);
    PageResponse<CouponLog> getAllLogs(int page, int size);

    PageResponse<CouponLog> searchLogs(LogSearchRequest request, int page, int size);
}
