package org.project.admin.repository.Log;

import org.project.admin.entity.Log.PatientLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientLogRepository extends JpaRepository<PatientLog,Long>, JpaSpecificationExecutor<PatientLog> {
    List<PatientLog> findByPatientIdOrderByLogTimeAsc(Long patientId);
}
