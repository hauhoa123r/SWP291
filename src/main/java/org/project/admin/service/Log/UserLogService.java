package org.project.admin.service.Log;

import org.project.admin.dto.request.LogSearchRequest;
import org.project.admin.dto.response.UserResponse;
import org.project.admin.entity.Log.UserLog;
import org.project.admin.entity.User;
import org.project.admin.enums.AuditAction;
import org.project.admin.util.PageResponse;

import java.util.List;

public interface UserLogService {
    void logUserAction(User user, AuditAction action);
    void logUserUpdateAction(UserResponse oldUser, UserResponse newUser, AuditAction action);
    List<UserLog> getLogsByUser(Long userId);
    PageResponse<UserLog> getAllLogs(int page, int size);

    PageResponse<UserLog> searchLogs(LogSearchRequest request, int page, int size);
}
