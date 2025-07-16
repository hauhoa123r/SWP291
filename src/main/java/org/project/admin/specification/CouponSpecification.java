package org.project.admin.specification;

import org.project.admin.dto.request.CouponSearchRequest;
import org.project.admin.entity.Coupon;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class CouponSpecification {

    public static Specification<Coupon> filter(CouponSearchRequest req) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if (req.getKeyword() != null && !req.getKeyword().trim().isEmpty()) {
                String kw = "%" + req.getKeyword().trim().toLowerCase() + "%";
                predicate = cb.and(predicate,
                        cb.or(
                                cb.like(cb.lower(root.get("code")), kw),
                                cb.like(cb.lower(root.get("description")), kw)
                        )
                );
            }
            if (req.getDiscountType() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("discountType"), req.getDiscountType()));
            }
            if (req.getStatus() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("status"), req.getStatus()));
            }
            if (req.getStartDateFrom() != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("startDate"), req.getStartDateFrom()));
            }
            if (req.getStartDateTo() != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("startDate"), req.getStartDateTo()));
            }
            if (req.getExpirationDateFrom() != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("expirationDate"), req.getExpirationDateFrom()));
            }
            if (req.getExpirationDateTo() != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("expirationDate"), req.getExpirationDateTo()));
            }
            if (req.getMinOrderAmountFrom() != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("minOrderAmount"), req.getMinOrderAmountFrom()));
            }
            if (req.getMinOrderAmountTo() != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("minOrderAmount"), req.getMinOrderAmountTo()));
            }

            return predicate;
        };
    }
}
