package org.project.admin.service.impl;

import org.project.admin.dto.request.CouponRequest;
import org.project.admin.dto.request.CouponSearchRequest;
import org.project.admin.dto.response.CouponResponse;
import org.project.admin.entity.Coupon;
import org.project.admin.enums.AuditAction;
import org.project.admin.enums.coupon.CouponStatus;
import org.project.admin.mapper.CouponMapper;
import org.project.admin.repository.CouponRepository;
import org.project.admin.service.CouponService;
import org.project.admin.service.Log.CouponLogService;
import org.project.admin.specification.CouponSpecification;
import org.project.admin.util.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("adminCouponServiceImpl")
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponMapper  couponMapper;

    @Autowired
    private CouponLogService couponLogService;

    @Override
    public List<CouponResponse> getAllCoupons() {
        List<Coupon> coupons = couponRepository.findAll();
        return coupons.stream()
                .map(couponMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CouponResponse getCouponById(Long couponId) {
        Optional<Coupon> coupon = couponRepository.findById(couponId);
        if (coupon.isPresent()) {
            return couponMapper.toResponse(coupon.get());
        } else {
            throw new RuntimeException("Không tìm thấy phiếu giảm giá");
        }
    }

    @Override
    public CouponResponse createCoupon(CouponRequest couponRequest) {
        Coupon coupon = couponMapper.toEntity(couponRequest);
        if (coupon.getUsedCount() == null) {
            coupon.setUsedCount(0);
        }
        Coupon savedCoupon = couponRepository.save(coupon);
        couponLogService.logCouponAction(savedCoupon, AuditAction.CREATE);
        return couponMapper.toResponse(savedCoupon);
    }


    @Override
    public CouponResponse updateCoupon(Long couponId, CouponRequest couponRequest) {
        Optional<Coupon> couponOptional = couponRepository.findById(couponId);
        if (couponOptional.isPresent()) {
            Coupon coupon = couponOptional.get();

            CouponResponse oldCoupon = couponMapper.toResponse(coupon);

            coupon.setCode(couponRequest.getCode());
            coupon.setDescription(couponRequest.getDescription());
            coupon.setDiscountType(couponRequest.getDiscountType());
            coupon.setValue(couponRequest.getValue());
            coupon.setExpirationDate(couponRequest.getExpirationDate());
            coupon.setStartDate(couponRequest.getStartDate());
            coupon.setMinOrderAmount(couponRequest.getMinOrderAmount());
            coupon.setUsageLimit(couponRequest.getUsageLimit());
            coupon.setUserUsageLimit(couponRequest.getUserUsageLimit());
            // Nếu muốn cho update usedCount từ phía admin, thì mở dòng sau:
            if (couponRequest.getUsedCount() != null) {
                coupon.setUsedCount(couponRequest.getUsedCount());
            }
            updateCouponStatus(coupon, couponRequest.getStatus());
            Coupon updatedCoupon = couponRepository.save(coupon);

            CouponResponse newCoupon = couponMapper.toResponse(updatedCoupon);
            couponLogService.logCouponUpdateAction(oldCoupon,newCoupon,AuditAction.UPDATE);
            return newCoupon;
        } else {
            throw new RuntimeException("Không tìm thấy phiếu giảm giá");
        }
    }


    @Override
    public void deleteCoupon(Long couponId) {
        Optional<Coupon> coupon = couponRepository.findById(couponId);
        if (coupon.isPresent()) {
            couponLogService.logCouponAction(coupon.get(), AuditAction.DELETE);
            couponRepository.delete(coupon.get());
        } else {
            throw new RuntimeException("Không tìm thấy phiếu giảm giá");
        }
    }


    @Override
    public PageResponse<CouponResponse> getCouponsWithPagination(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<Coupon> couponPage = couponRepository.findAll(pageRequest);

        return new PageResponse<>(couponPage.map(couponMapper::toResponse));
    }

    @Override
    public PageResponse<CouponResponse> searchCoupons(CouponSearchRequest req, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Coupon> couponPage = couponRepository.findAll(CouponSpecification.filter(req), pageRequest);
        return new PageResponse<>(couponPage.map(couponMapper::toResponse));
    }

    private void updateCouponStatus(Coupon coupon, CouponStatus requestStatus) {
        if (requestStatus == CouponStatus.INACTIVE) {
            coupon.setStatus(CouponStatus.INACTIVE);
        } else {
            if (coupon.getExpirationDate() != null && coupon.getExpirationDate().isBefore(LocalDate.now())) {
                coupon.setStatus(CouponStatus.EXPIRED);
            } else if (
                    coupon.getUsageLimit() != null && coupon.getUsedCount() != null
                            && coupon.getUsedCount() >= coupon.getUsageLimit()
            ) {
                coupon.setStatus(CouponStatus.USE_UP);
            } else {
                coupon.setStatus(CouponStatus.ACTIVE);
            }
        }
    }

}
