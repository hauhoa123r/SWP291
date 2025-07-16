package org.project.admin.repository;

import org.project.admin.entity.CouponUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("adminCouponUserRepository")
public interface CouponUserRepository extends JpaRepository<CouponUser, Long> {
    Page<CouponUser> findByCoupon_CouponId(Long couponId, Pageable pageable);
    List<CouponUser> findByCoupon_CouponId(Long couponId);
    boolean existsByCoupon_CouponIdAndUser_UserId(Long couponId, Long userId);
    void deleteByCoupon_CouponIdAndUser_UserId(Long couponId, Long userId);

}
