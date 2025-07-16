package org.project.admin.controller;

import org.project.admin.dto.request.CouponRequest;
import org.project.admin.dto.request.CouponSearchRequest;
import org.project.admin.dto.response.CouponResponse;
import org.project.admin.service.CouponService;
import org.project.admin.util.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/coupons")
public class CouponAdminController {

    @Autowired
    private CouponService couponService;


    @GetMapping
    public ResponseEntity<PageResponse<CouponResponse>> getCoupons(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        PageResponse<CouponResponse> couponPage = couponService.getCouponsWithPagination(page, size);
        return new ResponseEntity<>(couponPage, HttpStatus.OK);
    }

    @GetMapping("/{couponId}")
    public ResponseEntity<CouponResponse> getCouponById(@PathVariable Long couponId) {
        CouponResponse couponResponse = couponService.getCouponById(couponId);
        return new ResponseEntity<>(couponResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CouponResponse> createCoupon(@RequestBody CouponRequest couponRequest) {
        CouponResponse couponResponse = couponService.createCoupon(couponRequest);
        return new ResponseEntity<>(couponResponse, HttpStatus.CREATED);
    }

    @PostMapping("/search")
    public ResponseEntity<PageResponse<CouponResponse>> searchCoupons(
            @RequestBody CouponSearchRequest req,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        PageResponse<CouponResponse> result = couponService.searchCoupons(req, page, size);
        return ResponseEntity.ok(result);
    }



    @PutMapping("/{couponId}")
    public ResponseEntity<CouponResponse> updateCoupon(
            @PathVariable Long couponId, @RequestBody CouponRequest couponRequest) {
        CouponResponse couponResponse = couponService.updateCoupon(couponId, couponRequest);
        return new ResponseEntity<>(couponResponse, HttpStatus.OK);
    }


    @DeleteMapping("/{couponId}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long couponId) {
        couponService.deleteCoupon(couponId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
