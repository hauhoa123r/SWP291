package org.project.admin.repository;

import org.project.admin.entity.Staff;
import org.project.admin.enums.staffs.StaffRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("adminStaffRepository")
public interface StaffRepository extends JpaRepository<Staff, Long>, JpaSpecificationExecutor<Staff> {
    List<Staff> findByStaffRoleAndFullNameContainingIgnoreCase(StaffRole role, String name);
    List<Staff> findByStaffRole(StaffRole role);

    List<Staff> findByFullNameContainingIgnoreCase(String name);
}

