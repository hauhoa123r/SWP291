package org.project.admin.repository;

import org.project.admin.entity.StaffSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("adminStaffScheduleRepository")
public interface StaffScheduleRepository extends JpaRepository<StaffSchedule, Long> , JpaSpecificationExecutor<StaffSchedule> {

    // Lấy tất cả (không phân trang)
    List<StaffSchedule> findByStaff_StaffId(Long staffId);

    // Lấy có phân trang
    Page<StaffSchedule> findByStaff_StaffId(Long staffId, Pageable pageable);
}


