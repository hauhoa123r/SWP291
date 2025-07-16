package org.project.admin.repository.Log;

import org.project.admin.entity.Log.CouponLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponLogRepository extends JpaRepository<CouponLog, Long>, JpaSpecificationExecutor<CouponLog> {
    List<CouponLog> findByCouponIdOrderByLogTimeAsc(Long couponId);
}
