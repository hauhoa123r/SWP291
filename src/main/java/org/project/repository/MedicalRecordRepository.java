package org.project.repository;

import org.project.entity.MedicalRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalRecordRepository extends JpaRepository <MedicalRecordEntity, Long>{
    List<MedicalRecordEntity> findByPatientEntity_Id(Long patientId);
}