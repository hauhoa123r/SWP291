package org.project.admin.specification;

import org.project.admin.dto.request.StaffSearchRequest;
import org.project.admin.entity.Staff;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class StaffSpecification {

    public static Specification<Staff> filter(StaffSearchRequest req) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if (req.getKeyword() != null && !req.getKeyword().trim().isEmpty()) {
                String kw = "%" + req.getKeyword().trim().toLowerCase() + "%";
                Predicate byName = cb.like(cb.lower(root.get("fullName")), kw);
                Predicate byEmail = cb.like(cb.lower(root.get("user").get("email")), kw);
                Predicate byPhone = cb.like(cb.lower(root.get("user").get("phoneNumber")), kw);
                predicate = cb.and(predicate, cb.or(byName, byEmail, byPhone));
            }

            if (req.getUserId() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("user").get("userId"), req.getUserId()));
            }
            if (req.getStaffId() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("staffId"), req.getStaffId()));
            }

            if (req.getStaffRole() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("staffRole"), req.getStaffRole()));
            }
            if (req.getStaffType() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("staffType"), req.getStaffType()));
            }

            if (req.getDepartmentId() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("department").get("departmentId"), req.getDepartmentId()));
            }
            if (req.getHospitalId() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("hospital").get("hospitalId"), req.getHospitalId()));
            }

            if (req.getRankLevel() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("rankLevel"), req.getRankLevel()));
            }
            if (req.getHireDateFrom() != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("hireDate"), req.getHireDateFrom()));
            }
            if (req.getHireDateTo() != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("hireDate"), req.getHireDateTo()));
            }

            return predicate;
        };
    }
}
