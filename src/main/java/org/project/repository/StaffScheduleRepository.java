package org.project.repository;

import org.project.entity.StaffScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
@Repository
public interface StaffScheduleRepository extends JpaRepository<StaffScheduleEntity, Long> {
    List<StaffScheduleEntity> findByStaffEntityIdAndAvailableDate(Long staffEntityId, Date availableDate);
}
