package org.project.admin.specification;

import org.project.admin.dto.request.LogSearchRequest;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class LogSpecification {
    /**
     * @param <T>           Loại entity log (UserLog, StaffLog, ...)
     * @param req           Thông tin tìm kiếm
     * @param entityIdField Tên trường id entity ("userId", "staffId", "patientId", ...)
     */
    public static <T> Specification<T> filter(LogSearchRequest req, String entityIdField) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            // Lọc theo entityId (userId, staffId, ...)
            if (req.getEntityId() != null) {
                predicate = cb.and(predicate, cb.equal(root.get(entityIdField), req.getEntityId()));
            }
            // Lọc theo action
            if (req.getAction() != null && !req.getAction().isEmpty()) {
                predicate = cb.and(predicate, cb.equal(root.get("action"), req.getAction()));
            }
            // Lọc theo logTime từ
            if (req.getLogTimeFrom() != null && !req.getLogTimeFrom().isEmpty()) {
                try {
                    LocalDateTime from = LocalDateTime.parse(req.getLogTimeFrom());
                    predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("logTime"), from));
                } catch (DateTimeParseException ignored) {}
            }
            // Lọc theo logTime đến
            if (req.getLogTimeTo() != null && !req.getLogTimeTo().isEmpty()) {
                try {
                    LocalDateTime to = LocalDateTime.parse(req.getLogTimeTo());
                    predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("logTime"), to));
                } catch (DateTimeParseException ignored) {}
            }
            // Lọc theo keyword trong logDetail
            if (req.getKeyword() != null && !req.getKeyword().trim().isEmpty()) {
                String kw = "%" + req.getKeyword().trim().toLowerCase() + "%";
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("logDetail")), kw));
            }

            return predicate;
        };
    }
}
