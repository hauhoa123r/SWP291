package org.project.admin.service.impl;

import org.project.admin.dto.request.CouponProductRequest;
import org.project.admin.dto.response.CouponProductResponse;
import org.project.admin.entity.Coupon;
import org.project.admin.entity.CouponProduct;
import org.project.admin.entity.Product;
import org.project.admin.mapper.CouponProductMapper;
import org.project.admin.repository.CouponProductRepository;
import org.project.admin.repository.CouponRepository;
import org.project.admin.repository.ProductRepository;
import org.project.admin.service.CouponProductService;
import org.project.admin.util.PageResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("adminCouponProductServiceImpl")
@RequiredArgsConstructor
public class CouponProductServiceImpl implements CouponProductService {
    private final CouponRepository couponRepository;
    private final ProductRepository productRepository;
    private final CouponProductRepository couponProductRepository;
    private final CouponProductMapper couponProductMapper;

    @Override
    @Transactional
    public void assignProductsToCoupon(CouponProductRequest request) {
        Coupon coupon = couponRepository.findById(request.getCouponId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phiếu giảm giá"));
        for (Long pid : request.getProductIds()) {
            Product product = productRepository.findById(pid)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));
            if (!couponProductRepository.existsByCoupon_CouponIdAndProduct_ProductId(request.getCouponId(), pid)) {
                CouponProduct cp = new CouponProduct();
                cp.setCoupon(coupon);
                cp.setProduct(product);
                couponProductRepository.save(cp);
            }
        }
    }

    @Override
    @Transactional
    public void removeProductFromCoupon(Long couponId, Long productId) {
        couponProductRepository.deleteByCoupon_CouponIdAndProduct_ProductId(couponId, productId);
    }

    @Override
    public List<CouponProductResponse> getProductsByCoupon(Long couponId) {
        List<CouponProduct> cps = couponProductRepository.findByCoupon_CouponId(couponId);
        return cps.stream().map(couponProductMapper::toResponse).toList();
    }

    @Override
    public PageResponse<CouponProductResponse> getProductsByCoupon(Long couponId, int page, int size) {
        Page<CouponProduct> cps = couponProductRepository.findByCoupon_CouponId(
                couponId,
                PageRequest.of(page, size)
        );
        Page<CouponProductResponse> productPage = cps.map(couponProductMapper::toResponse);
        return new PageResponse<>(productPage);
    }

}
