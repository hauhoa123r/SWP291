package org.project.admin.service.impl.Log;

import org.project.admin.dto.request.LogSearchRequest;
import org.project.admin.dto.response.CouponResponse;
import org.project.admin.entity.Coupon;
import org.project.admin.entity.Log.CouponLog;
import org.project.admin.enums.AuditAction;
import org.project.admin.repository.Log.CouponLogRepository;
import org.project.admin.service.Log.CouponLogService;
import org.project.admin.specification.LogSpecification;
import org.project.admin.util.PageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CouponLogServiceImpl implements CouponLogService {

    private final CouponLogRepository couponLogRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void logCouponAction(Coupon coupon, AuditAction action) {
        CouponLog log = new CouponLog();
        log.setCouponId(coupon.getCouponId());
        log.setAction(action);
        log.setLogTime(LocalDateTime.now());

        try{
            log.setLogDetail(objectMapper.writeValueAsString(coupon));
        } catch (Exception e) {
            log.setLogDetail("Lỗi: " + e.getMessage());
        }
        couponLogRepository.save(log);
    }

    @Override
    public void logCouponUpdateAction(CouponResponse oldCoupon, CouponResponse newCoupon, AuditAction action) {
        CouponLog log = new CouponLog();
        log.setCouponId(oldCoupon.getCouponId());
        log.setAction(action);
        log.setLogTime(LocalDateTime.now());

        StringBuilder detail = new StringBuilder();

        // So sánh các trường string/enum như cũ
        if (!Objects.equals(oldCoupon.getCode(), newCoupon.getCode())) {
            detail.append(String.format("Mã: '%s' → '%s'\n", oldCoupon.getCode(), newCoupon.getCode()));
        }
        if (!Objects.equals(oldCoupon.getDescription(), newCoupon.getDescription())) {
            detail.append(String.format("Mô tả: '%s' → '%s'\n", oldCoupon.getDescription(), newCoupon.getDescription()));
        }
        if (!Objects.equals(oldCoupon.getDiscountType(), newCoupon.getDiscountType())) {
            detail.append(String.format("Loại giảm giá: '%s' → '%s'\n", oldCoupon.getDiscountType(), newCoupon.getDiscountType()));
        }
        // ==== So sánh BigDecimal cho value ====
        if (!isBigDecimalEquals(oldCoupon.getValue(), newCoupon.getValue())) {
            detail.append(String.format("Giá trị: '%s' → '%s'\n",
                    formatBigDecimal(oldCoupon.getValue()), formatBigDecimal(newCoupon.getValue())));
        }
        // ==== So sánh ngày ====
        if (!Objects.equals(oldCoupon.getStartDate(), newCoupon.getStartDate())) {
            detail.append(String.format("Ngày bắt đầu: '%s' → '%s'\n", oldCoupon.getStartDate(), newCoupon.getStartDate()));
        }
        if (!Objects.equals(oldCoupon.getExpirationDate(), newCoupon.getExpirationDate())) {
            detail.append(String.format("Ngày hết hạn: '%s' → '%s'\n", oldCoupon.getExpirationDate(), newCoupon.getExpirationDate()));
        }
        if (!Objects.equals(oldCoupon.getUsageLimit(), newCoupon.getUsageLimit())) {
            detail.append(String.format("Số lần sử dụng tối đa: '%s' → '%s'\n", oldCoupon.getUsageLimit(), newCoupon.getUsageLimit()));
        }
        if (!Objects.equals(oldCoupon.getUserUsageLimit(), newCoupon.getUserUsageLimit())) {
            detail.append(String.format("Số lần sử dụng tối đa mỗi người: '%s' → '%s'\n", oldCoupon.getUserUsageLimit(), newCoupon.getUserUsageLimit()));
        }
        if (!Objects.equals(oldCoupon.getUsedCount(), newCoupon.getUsedCount())) {
            detail.append(String.format("Đã sử dụng: '%s' → '%s'\n", oldCoupon.getUsedCount(), newCoupon.getUsedCount()));
        }
        // ==== So sánh BigDecimal cho minOrderAmount ====
        if (!isBigDecimalEquals(oldCoupon.getMinOrderAmount(), newCoupon.getMinOrderAmount())) {
            detail.append(String.format("Giá trị đơn tối thiểu: '%s' → '%s'\n",
                    formatBigDecimal(oldCoupon.getMinOrderAmount()), formatBigDecimal(newCoupon.getMinOrderAmount())));
        }
        if (!Objects.equals(oldCoupon.getStatus(), newCoupon.getStatus())) {
            detail.append(String.format("Trạng thái: '%s' → '%s'\n", oldCoupon.getStatus(), newCoupon.getStatus()));
        }

        if (detail.length() == 0) {
            detail.append("Không có thay đổi.");
        }

        log.setLogDetail(detail.toString());
        couponLogRepository.save(log);
    }
    @Override
    public List<CouponLog> getLogsByCoupon(Long couponId) {
        return couponLogRepository.findByCouponIdOrderByLogTimeAsc(couponId);
    }

    @Override
    public PageResponse<CouponLog> getAllLogs(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by("logTime").descending());
        Page<CouponLog> logPage = couponLogRepository.findAll(pageable);
        return new PageResponse<>(logPage);
    }

    private boolean isBigDecimalEquals(java.math.BigDecimal a, java.math.BigDecimal b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        return a.compareTo(b) == 0;
    }

    private String formatBigDecimal(java.math.BigDecimal value) {
        if (value == null) return "null";
        return value.stripTrailingZeros().toPlainString();
    }

    @Override
    public PageResponse<CouponLog> searchLogs(LogSearchRequest request, int page, int size){
        Specification<CouponLog> spec = LogSpecification.filter(request, "couponId");
        Page<CouponLog> logPage = couponLogRepository.findAll(spec, PageRequest.of(page, size, Sort.by("logTime").descending()));
        return new PageResponse<>(logPage);
    }

}
