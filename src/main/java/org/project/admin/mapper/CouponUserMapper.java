package org.project.admin.mapper;

import org.project.admin.dto.request.CouponUserRequest;
import org.project.admin.dto.response.CouponUserResponse;
import org.project.admin.entity.Coupon;
import org.project.admin.entity.CouponUser;
import org.project.admin.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CouponUserMapper {
    @Mapping(source = "coupon.couponId", target = "couponId")
    @Mapping(source = "coupon.code", target = "couponCode")
    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "user.email", target = "userEmail")
    @Mapping(source = "user.phoneNumber", target = "userPhoneNumber")
    CouponUserResponse toResponse(CouponUser entity);

    List<CouponUserResponse> toResponseList(List<CouponUser> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "coupon", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "usedCount", ignore = true)
    CouponUser toEntity(CouponUserRequest request);

    default CouponUser toEntity(CouponUserRequest request, Coupon coupon, User user) {
        CouponUser entity = toEntity(request);
        entity.setCoupon(coupon);
        entity.setUser(user);
        entity.setUsedCount(0);
        return entity;
    }
}


