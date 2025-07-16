package org.project.admin.specification;

import org.project.admin.dto.request.PatientSearchRequest;
import org.project.admin.entity.Patient;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class PatientSpecification {

    public static Specification<Patient> filter(PatientSearchRequest req) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();

            if (req.getKeyword() != null && !req.getKeyword().trim().isEmpty()) {
                String pattern = "%" + req.getKeyword().trim().toLowerCase() + "%";
                Predicate byName = cb.like(cb.lower(root.get("fullName")), pattern);
                Predicate byEmail = cb.like(cb.lower(root.get("email")), pattern);
                Predicate byPhone = cb.like(cb.lower(root.get("phoneNumber")), pattern);
                predicate = cb.and(predicate, cb.or(byName, byEmail, byPhone));
            }
            if (req.getUserId() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("user").get("userId"), req.getUserId()));
            }
            if (req.getPatientId() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("patientId"), req.getPatientId()));
            }

            if (req.getGender() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("gender"), req.getGender()));
            }
            if (req.getRelationship() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("relationship"), req.getRelationship()));
            }
            if (req.getBloodType() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("bloodType"), req.getBloodType()));
            }
            if (req.getBirthdateFrom() != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("birthdate"), req.getBirthdateFrom()));
            }
            if (req.getBirthdateTo() != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("birthdate"), req.getBirthdateTo()));
            }
            if (req.getAddress() != null && !req.getAddress().trim().isEmpty()) {
                String addrPattern = "%" + req.getAddress().trim().toLowerCase() + "%";
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("address")), addrPattern));
            }
            return predicate;
        };
    }
}

