package org.project.admin.controller.Log;

import org.project.admin.dto.request.LogSearchRequest;
import org.project.admin.entity.Log.StaffScheduleLog;
import org.project.admin.service.Log.StaffScheduleLogService;
import org.project.admin.util.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/staff-schedules/logs")
@RequiredArgsConstructor
public class StaffScheduleLogController {

    private final StaffScheduleLogService staffScheduleLogService;

    @GetMapping
    public PageResponse<StaffScheduleLog> getAllLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return staffScheduleLogService.getAllLogs(page, size);
    }

    @GetMapping("/staffSchedule/{staffScheduleId}")
    public List<StaffScheduleLog> getLogsByStaffSchedule(@PathVariable Long staffScheduleId){
        return staffScheduleLogService.getLogsByStaffSchedule(staffScheduleId);
    }

    @PostMapping("/search")
    public PageResponse<StaffScheduleLog> searchLogs(
            @RequestBody LogSearchRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return staffScheduleLogService.searchLogs(request, page, size);
    }
}
