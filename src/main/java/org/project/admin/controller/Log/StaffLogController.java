package org.project.admin.controller.Log;

import org.project.admin.dto.request.LogSearchRequest;
import org.project.admin.entity.Log.StaffLog;
import org.project.admin.service.Log.StaffLogService;
import org.project.admin.util.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/staff/logs")
@RequiredArgsConstructor
public class StaffLogController {
    private final StaffLogService staffLogService;

    //Lấy tất cả số log
    @GetMapping
    public PageResponse<StaffLog> getAllLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return staffLogService.getAllLogs(page, size);
    }

    // Lấy log của một nhân viên theo id
    @GetMapping("/staff/{staffId}")
    public List<StaffLog> getLogsByStaff(@PathVariable Long staffId) {
        return staffLogService.getLogsByStaff(staffId);
    }

    @PostMapping("/search")
    public PageResponse<StaffLog> searchLogs(
            @RequestBody LogSearchRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return staffLogService.searchLogs(request, page, size);
    }
}
