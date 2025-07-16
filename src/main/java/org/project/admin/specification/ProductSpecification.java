package org.project.admin.specification;

import org.project.admin.dto.request.ProductSearchRequest;
import org.project.admin.entity.Product;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    public static Specification<Product> filter(ProductSearchRequest req) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            // Search by keyword (name or description, case-insensitive)
            if (req.getKeyword() != null && !req.getKeyword().trim().isEmpty()) {
                String pattern = "%" + req.getKeyword().trim().toLowerCase() + "%";
                Predicate byName = cb.like(cb.lower(root.get("name")), pattern);
                Predicate byDescription = cb.like(cb.lower(root.get("description")), pattern);
                predicate = cb.and(predicate, cb.or(byName, byDescription));
            }

            if (req.getProductType() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("productType"), req.getProductType()));
            }

            if (req.getProductStatus() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("productStatus"), req.getProductStatus()));
            }

            if (req.getLabel() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("label"), req.getLabel()));
            }

            if (req.getUnit() != null && !req.getUnit().trim().isEmpty()) {
                predicate = cb.and(predicate, cb.equal(cb.lower(root.get("unit")), req.getUnit().trim().toLowerCase()));
            }

            if (req.getPriceFrom() != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("price"), req.getPriceFrom()));
            }
            if (req.getPriceTo() != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("price"), req.getPriceTo()));
            }

            return predicate;
        };
    }
}
