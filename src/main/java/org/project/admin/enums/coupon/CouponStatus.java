package org.project.admin.enums.coupon;

public enum CouponStatus {
    ACTIVE,      // Đang hoạt động
    INACTIVE,    // Không hoạt động (admin tắt)
    EXPIRED,     // Hết hạn (expirationDate < now)
    USE_UP       // Đã hết lượt sử dụng
}
