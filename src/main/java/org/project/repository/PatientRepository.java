package org.project.repository;

import org.project.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long> {
    List<PatientEntity> findByUserEntity_Id(Long userId);
    @Query("SELECT p FROM PatientEntityEntity p WHERE FUNCTION('MONTH', p.birthdate) = :month AND FUNCTION('DAY', p.birthdate) = :day")
    List<PatientEntity> findByBirthDateMonthDay(@Param("month") int month, @Param("day") int day);

}
