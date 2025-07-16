package org.project.admin.service.impl;

import org.project.admin.dto.request.CouponUserRequest;
import org.project.admin.dto.response.CouponUserResponse;
import org.project.admin.entity.Coupon;
import org.project.admin.entity.CouponUser;
import org.project.admin.entity.User;
import org.project.admin.mapper.CouponUserMapper;
import org.project.admin.repository.CouponRepository;
import org.project.admin.repository.CouponUserRepository;
import org.project.admin.repository.UserRepository;
import org.project.admin.service.CouponUserService;
import org.project.admin.util.PageResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("adminCouponUserServiceImpl")
@RequiredArgsConstructor
public class CouponUserServiceImpl implements CouponUserService {

    private final CouponRepository couponRepository;
    private final UserRepository userRepository;
    private final CouponUserRepository couponUserRepository;
    private final CouponUserMapper couponUserMapper;

    @Override
    @Transactional
    public void assignUsersToCoupon(CouponUserRequest request) {
        Coupon coupon = couponRepository.findById(request.getCouponId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phiếu giảm giá"));
        for (Long userId : request.getUserIds()) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
            if (!couponUserRepository.existsByCoupon_CouponIdAndUser_UserId(request.getCouponId(), userId)) {
                CouponUser cu = new CouponUser();
                cu.setCoupon(coupon);
                cu.setUser(user);
                cu.setUsedCount(0);
                couponUserRepository.save(cu);
            }
        }
    }

    @Override
    @Transactional
    public void removeUserFromCoupon(Long couponId, Long userId) {
        couponUserRepository.deleteByCoupon_CouponIdAndUser_UserId(couponId, userId);
    }

    @Override
    public List<CouponUserResponse> getUsersByCoupon(Long couponId) {
        List<CouponUser> cus = couponUserRepository.findByCoupon_CouponId(couponId);
        return couponUserMapper.toResponseList(cus);
    }

    @Override
    public PageResponse<CouponUserResponse> getUsersByCoupon(Long couponId, int page, int size) {
        Page<CouponUser> pageData = couponUserRepository.findByCoupon_CouponId(couponId, PageRequest.of(page, size));
        Page<CouponUserResponse> mapped = pageData.map(couponUserMapper::toResponse);
        return new PageResponse<>(mapped);
    }
}
