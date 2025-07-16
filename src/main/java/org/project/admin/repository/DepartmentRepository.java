package org.project.admin.repository;

import org.project.admin.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("adminDepartmentRepository")
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    interface DepartmentStats {
        Long getDepartmentId();
        Long getCompletedAppointments();
        Long getDoctorCount();
    }

    @Query("""
    SELECT 
        d.departmentId AS departmentId,
        COUNT(DISTINCT CASE WHEN a.appointmentStatus = 'COMPLETED' THEN a.appointmentId END) AS completedAppointments,
        COUNT(DISTINCT s.staffId) AS doctorCount
    FROM Department d
    LEFT JOIN Staff s ON s.department = d AND s.staffRole = 'DOCTOR'
    LEFT JOIN Appointment a ON a.doctor = s AND a.appointmentStatus = 'COMPLETED'
    WHERE s.hospital.hospitalId = :hospitalId 
    GROUP BY d.departmentId
""")
    List<DepartmentStats> getDepartmentStatsByHospital(Long hospitalId);



    // Truy vấn các department có staff thuộc hospital_id này
    @Query("""
        SELECT DISTINCT d
        FROM Department d
        JOIN Staff s ON s.department = d
        WHERE s.hospital.hospitalId = :hospitalId
    """)
    List<Department> findByHospitalId(Long hospitalId);

    @Query("""
        SELECT DISTINCT d
        FROM Department d
        JOIN Staff s ON s.department = d
        WHERE s.hospital.hospitalId = :hospitalId
    """)
    Page<Department> findByHospitalId(Long hospitalId, Pageable pageable);
}
