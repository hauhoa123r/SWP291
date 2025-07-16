package org.project.admin.repository;

import org.project.admin.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("adminHospitalRepository")
public interface HospitalRepository extends JpaRepository<Hospital, Long> {
}
