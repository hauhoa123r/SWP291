package org.project.admin.repository.Log;

import org.project.admin.entity.Log.StaffScheduleLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface StaffScheduleLogRepository extends JpaRepository<StaffScheduleLog, Long>, JpaSpecificationExecutor<StaffScheduleLog> {
    List<StaffScheduleLog> findByStaffScheduleIdOrderByLogTimeAsc(Long staffScheduleId);
}
