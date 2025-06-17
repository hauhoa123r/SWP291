package org.project.repository;

import org.project.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long> {

    List<PatientEntity> findAllByUserEntity_Id(Long userId);

}
