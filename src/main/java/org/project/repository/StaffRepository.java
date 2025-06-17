package org.project.repository;

import org.project.entity.StaffEntity;
import org.project.enums.StaffRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<StaffEntity, Long> {
    Page<StaffEntity> findAllByStaffRole(StaffRole staffRole, Pageable pageable);

    Page<StaffEntity> findAllByStaffRoleAndDepartmentEntityName(StaffRole staffRole, String departmentEntityName, Pageable pageable);

    List<StaffEntity> findAllByStaffRoleAndDepartmentEntityNameAndIdIsNot(StaffRole staffRole, String departmentEntityName, Long id);

    StaffEntity findByStaffRoleAndId(StaffRole staffRole, Long id);

    List<StaffEntity> findAllByStaffRole(StaffRole staffRole);

    boolean existsByStaffRoleAndId(StaffRole staffRole, Long id);
}
