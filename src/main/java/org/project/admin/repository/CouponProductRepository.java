package org.project.admin.repository;

import org.project.admin.entity.CouponProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("adminCouponProductRepository")
public interface CouponProductRepository extends JpaRepository<CouponProduct, Long> {
    List<CouponProduct> findByCoupon_CouponId(Long couponId);
    List<CouponProduct> findByProduct_ProductId(Long productId);
    boolean existsByCoupon_CouponIdAndProduct_ProductId(Long couponId, Long productId);
    void deleteByCoupon_CouponIdAndProduct_ProductId(Long couponId, Long productId);
    Page<CouponProduct> findByCoupon_CouponId(Long couponId, Pageable pageable);

}

