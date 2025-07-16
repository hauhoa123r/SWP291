package org.project.admin.mapper;

import org.project.admin.dto.request.CouponRequest;
import org.project.admin.dto.response.CouponResponse;
import org.project.admin.entity.Coupon;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CouponMapper {
    Coupon toEntity(CouponRequest request);

    CouponResponse toResponse(Coupon coupon);
}
