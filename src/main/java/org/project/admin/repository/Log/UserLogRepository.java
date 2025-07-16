package org.project.admin.repository.Log;

import org.project.admin.entity.Log.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserLogRepository extends JpaRepository<UserLog,Long>, JpaSpecificationExecutor<UserLog> {
    List<UserLog> findByUserIdOrderByLogTimeAsc(Long userId);
}
