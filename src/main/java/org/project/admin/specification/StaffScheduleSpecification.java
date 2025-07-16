package org.project.admin.specification;

import org.project.admin.dto.request.StaffScheduleSearchRequest;
import org.project.admin.entity.StaffSchedule;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class StaffScheduleSpecification {

    public static Specification<StaffSchedule> filter(StaffScheduleSearchRequest req) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if (req.getStaffId() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("staff").get("staffId"), req.getStaffId()));
            }

            if (req.getAvailableDate() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("availableDate"), req.getAvailableDate()));
            }

            if (req.getAvailableDateFrom() != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("availableDate"), req.getAvailableDateFrom()));
            }
            if (req.getAvailableDateTo() != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("availableDate"), req.getAvailableDateTo()));
            }

            if (req.getStartTimeFrom() != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("startTime"), req.getStartTimeFrom()));
            }
            if (req.getStartTimeTo() != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("startTime"), req.getStartTimeTo()));
            }

            if (req.getEndTimeFrom() != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("endTime"), req.getEndTimeFrom()));
            }
            if (req.getEndTimeTo() != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("endTime"), req.getEndTimeTo()));
            }

            if (req.getName() != null && !req.getName().trim().isEmpty()) {
                String kw = "%" + req.getName().trim().toLowerCase() + "%";
                Predicate byStaffName = cb.like(cb.lower(root.get("staff").get("fullName")), kw);
                predicate = cb.and(predicate, byStaffName);
            }

            if (req.getStaffRole() != null && !req.getStaffRole().trim().isEmpty()) {
                predicate = cb.and(predicate, cb.equal(root.get("staff").get("staffRole"), req.getStaffRole()));
            }


            return predicate;
        };
    }
}
