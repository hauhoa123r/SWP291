package org.project.admin.service.Log;

import org.project.admin.dto.request.LogSearchRequest;
import org.project.admin.dto.response.StaffResponse;
import org.project.admin.entity.Log.StaffLog;
import org.project.admin.entity.Staff;
import org.project.admin.enums.AuditAction;
import org.project.admin.util.PageResponse;

import java.util.List;

public interface StaffLogService {
    void logStaffAction(Staff staff, AuditAction action);
    void logStaffUpdateAction(StaffResponse oldStaff, StaffResponse newStaff, AuditAction action);
    List<StaffLog> getLogsByStaff(Long staffId);
    PageResponse<StaffLog> getAllLogs(int page, int size);

    PageResponse<StaffLog> searchLogs(LogSearchRequest request, int page, int size);
}
