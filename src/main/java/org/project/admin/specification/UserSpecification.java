package org.project.admin.specification;

import org.project.admin.dto.request.UserSearchRequest;
import org.project.admin.entity.User;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static Specification<User> filter(UserSearchRequest req) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if (req.getUserId() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("userId"), req.getUserId()));
            }
            if (req.getEmail() != null && !req.getEmail().trim().isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("email")), "%" + req.getEmail().trim().toLowerCase() + "%"));
            }
            if (req.getPhoneNumber() != null && !req.getPhoneNumber().trim().isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("phoneNumber")), "%" + req.getPhoneNumber().trim().toLowerCase() + "%"));
            }
            if (req.getUserRole() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("userRole"), req.getUserRole()));
            }
            if (req.getUserStatus() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("userStatus"), req.getUserStatus()));
            }
            if (req.getIsVerified() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("isVerified"), req.getIsVerified()));
            }
            if (req.getTwoFactorEnabled() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("twoFactorEnabled"), req.getTwoFactorEnabled()));
            }

            predicate = cb.and(predicate, cb.equal(root.get("deleted"), false));
            return predicate;
        };
    }
}
