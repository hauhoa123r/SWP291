package org.project.repository;

import org.project.entity.MedicalRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MedicalRecordRepository extends JpaRepository <MedicalRecordEntity, Long>{
    List<MedicalRecordEntity> findByPatientEntity_Id(Long patientId);
}