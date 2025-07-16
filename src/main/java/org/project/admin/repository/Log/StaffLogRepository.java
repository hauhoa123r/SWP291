package org.project.admin.repository.Log;

import org.project.admin.entity.Log.StaffLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffLogRepository extends JpaRepository<StaffLog, Long>, JpaSpecificationExecutor<StaffLog> {
    List<StaffLog> findByStaffIdOrderByLogTimeAsc(Long staffId);
}
