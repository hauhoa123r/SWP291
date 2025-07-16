package org.project.admin.mapper;

import org.project.admin.dto.response.CouponProductResponse;
import org.project.admin.entity.CouponProduct;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CouponProductMapper {
    @Mapping(source = "coupon.couponId", target = "couponId")
    @Mapping(source = "coupon.code", target = "couponCode")
    @Mapping(source = "product.productId", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    CouponProductResponse toResponse(CouponProduct entity);
}
