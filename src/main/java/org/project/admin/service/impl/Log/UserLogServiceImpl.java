package org.project.admin.service.impl.Log;

import org.project.admin.dto.request.LogSearchRequest;
import org.project.admin.dto.response.UserResponse;
import org.project.admin.entity.Log.UserLog;
import org.project.admin.entity.User;
import org.project.admin.enums.AuditAction;
import org.project.admin.repository.Log.UserLogRepository;
import org.project.admin.service.Log.UserLogService;
import org.project.admin.specification.LogSpecification;
import org.project.admin.util.PageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserLogServiceImpl implements UserLogService {
    private final UserLogRepository userLogRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void logUserAction(User user, AuditAction action) {
        UserLog log = new UserLog();
        log.setUserId(user.getUserId());
        log.setAction(action);
        log.setLogTime(LocalDateTime.now());
        try {
            log.setLogDetail(objectMapper.writeValueAsString(user));
        } catch (Exception e) {
            log.setLogDetail("Lỗi: " + e.getMessage());
        }
        userLogRepository.save(log);
    }

    @Override
    public void logUserUpdateAction(UserResponse oldUser, UserResponse newUser, AuditAction action) {
        UserLog log = new UserLog();
        log.setUserId(oldUser.getUserId());
        log.setAction(action);
        log.setLogTime(LocalDateTime.now());

        StringBuilder detail = new StringBuilder();

        if (!Objects.equals(oldUser.getUserId(), newUser.getUserId())) {
            detail.append(String.format("UserId: '%s' → '%s'\n", oldUser.getUserId(), newUser.getUserId()));
        }
        if (!Objects.equals(oldUser.getEmail(), newUser.getEmail())) {
            detail.append(String.format("Email: '%s' → '%s'\n", oldUser.getEmail(), newUser.getEmail()));
        }
        if (!Objects.equals(oldUser.getPhoneNumber(), newUser.getPhoneNumber())) {
            detail.append(String.format("SĐT: '%s' → '%s'\n", oldUser.getPhoneNumber(), newUser.getPhoneNumber()));
        }
        if (!Objects.equals(oldUser.getUserRole(), newUser.getUserRole())) {
            detail.append(String.format("Vai trò: '%s' → '%s'\n", oldUser.getUserRole(), newUser.getUserRole()));
        }
        if (!Objects.equals(oldUser.getUserStatus(), newUser.getUserStatus())) {
            detail.append(String.format("Trạng thái: '%s' → '%s'\n", oldUser.getUserStatus(), newUser.getUserStatus()));
        }
        if (!Objects.equals(oldUser.getIsVerified(), newUser.getIsVerified())) {
            detail.append(String.format("Đã xác thực: '%s' → '%s'\n", oldUser.getIsVerified(), newUser.getIsVerified()));
        }
        if (!Objects.equals(oldUser.getTwoFactorEnabled(), newUser.getTwoFactorEnabled())) {
            detail.append(String.format("Xác thực 2 bước: '%s' → '%s'\n", oldUser.getTwoFactorEnabled(), newUser.getTwoFactorEnabled()));
        }

        if (detail.length() == 0) {
            detail.append("Không có thay đổi.");
        }

        log.setLogDetail(detail.toString());
        userLogRepository.save(log);
    }

    @Override
    public List<UserLog> getLogsByUser(Long userId) {
        return userLogRepository.findByUserIdOrderByLogTimeAsc(userId);
    }

    @Override
    public PageResponse<UserLog> getAllLogs(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by("logTime").descending());
        Page<UserLog> logPage = userLogRepository.findAll(pageable);
        return new PageResponse<>(logPage);
    }

    @Override
    public PageResponse<UserLog> searchLogs(LogSearchRequest request, int page, int size) {
        Specification<UserLog> spec = LogSpecification.filter(request, "userId");
        Page<UserLog> logPage = userLogRepository.findAll(spec, PageRequest.of(page, size, Sort.by("logTime").descending()));
        return new PageResponse<>(logPage);
    }

}
