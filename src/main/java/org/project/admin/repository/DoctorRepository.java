package org.project.admin.repository;

import org.project.admin.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("adminDoctorRepository")
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    @Query("""
    SELECT d FROM Doctor d
    JOIN Staff s ON d.doctorId = s.staffId
    WHERE LOWER(s.fullName) LIKE LOWER(CONCAT('%', :name, '%'))
    """)
    List<Doctor> findByStaffNameContainingIgnoreCase(@Param("name") String name);
}
