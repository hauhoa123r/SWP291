package org.project.repository;

import org.project.entity.MedicalProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicalProfileRepository extends JpaRepository<MedicalProfileEntity, Long> {
    Optional<MedicalProfileEntity> findByPatientEntity_Id(Long patientId);
}
