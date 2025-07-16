package org.project.admin.service.Log;

import org.project.admin.dto.request.LogSearchRequest;
import org.project.admin.dto.response.StaffScheduleResponse;
import org.project.admin.entity.Log.StaffScheduleLog;
import org.project.admin.entity.StaffSchedule;
import org.project.admin.enums.AuditAction;
import org.project.admin.util.PageResponse;

import java.util.List;

public interface StaffScheduleLogService {
    void logStaffScheduleAction(StaffSchedule staffSchedule, AuditAction action);
    void logStaffScheduleUpdateAction(StaffScheduleResponse oldStaffSchedule, StaffScheduleResponse newStaffSchedule, AuditAction action) ;
    List<StaffScheduleLog> getLogsByStaffSchedule(Long staffScheduleId);
    PageResponse<StaffScheduleLog> getAllLogs(int page, int size);

    PageResponse<StaffScheduleLog> searchLogs(LogSearchRequest request, int page, int size);
}
