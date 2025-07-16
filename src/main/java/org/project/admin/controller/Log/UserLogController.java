package org.project.admin.controller.Log;

import org.project.admin.dto.request.LogSearchRequest;
import org.project.admin.entity.Log.UserLog;
import org.project.admin.service.Log.UserLogService;
import org.project.admin.util.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/user/logs")
@RequiredArgsConstructor
public class UserLogController {
    private final UserLogService userLogService;

    // Lấy tất cả log của user (phân trang)
    @GetMapping
    public PageResponse<UserLog> getAllLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return userLogService.getAllLogs(page, size);
    }

    // Lấy log của một user theo id
    @GetMapping("/user/{userId}")
    public List<UserLog> getLogsByUser(@PathVariable Long userId) {
        return userLogService.getLogsByUser(userId);
    }

    @PostMapping("/search")
    public PageResponse<UserLog> searchLogs(
            @RequestBody LogSearchRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return userLogService.searchLogs(request, page, size);
    }
}
