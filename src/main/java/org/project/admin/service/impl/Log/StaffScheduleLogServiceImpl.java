package org.project.admin.service.impl.Log;

import org.project.admin.dto.request.LogSearchRequest;
import org.project.admin.dto.response.StaffScheduleResponse;
import org.project.admin.entity.Log.StaffScheduleLog;
import org.project.admin.entity.StaffSchedule;
import org.project.admin.enums.AuditAction;
import org.project.admin.mapper.StaffScheduleMapper;
import org.project.admin.repository.Log.StaffScheduleLogRepository;
import org.project.admin.service.Log.StaffScheduleLogService;
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

@Service
@RequiredArgsConstructor
public class StaffScheduleLogServiceImpl implements StaffScheduleLogService {

    private final StaffScheduleLogRepository staffScheduleLogRepository;
    private final ObjectMapper objectMapper;
    private final StaffScheduleMapper staffScheduleMapper;

    @Override
    public void logStaffScheduleAction(StaffSchedule staffSchedule, AuditAction action) {
        StaffScheduleLog log = new StaffScheduleLog();
        log.setStaffScheduleId(staffSchedule.getStaffScheduleId());
        log.setAction(action);
        log.setLogTime(LocalDateTime.now());
        try {
            StaffScheduleResponse dto = staffScheduleMapper.toResponse(staffSchedule);
            log.setLogDetail(objectMapper.writeValueAsString(dto));
        } catch (Exception e) {
            log.setLogDetail("Lỗi: " + e.getMessage());
        }
        staffScheduleLogRepository.save(log);
    }

    @Override
    public void logStaffScheduleUpdateAction(
            StaffScheduleResponse oldStaffSchedule,
            StaffScheduleResponse newStaffSchedule,
            AuditAction action) {
        StaffScheduleLog log = new StaffScheduleLog();
        log.setStaffScheduleId(oldStaffSchedule.getStaffScheduleId());
        log.setAction(action);
        log.setLogTime(LocalDateTime.now());

        StringBuilder detail = new StringBuilder();

        if (!java.util.Objects.equals(oldStaffSchedule.getStaffId(), newStaffSchedule.getStaffId())) {
            detail.append(String.format("ID nhân viên: '%s' → '%s'\n",
                    oldStaffSchedule.getStaffId(), newStaffSchedule.getStaffId()));
        }
        if (!java.util.Objects.equals(oldStaffSchedule.getStaffFullName(), newStaffSchedule.getStaffFullName())) {
            detail.append(String.format("Tên nhân viên: '%s' → '%s'\n",
                    oldStaffSchedule.getStaffFullName(), newStaffSchedule.getStaffFullName()));
        }
        if (!java.util.Objects.equals(oldStaffSchedule.getStaffAvatarUrl(), newStaffSchedule.getStaffAvatarUrl())) {
            detail.append(String.format("Ảnh đại diện: '%s' → '%s'\n",
                    oldStaffSchedule.getStaffAvatarUrl(), newStaffSchedule.getStaffAvatarUrl()));
        }
        if (!java.util.Objects.equals(oldStaffSchedule.getStaffRole(), newStaffSchedule.getStaffRole())) {
            detail.append(String.format("Chức vụ: '%s' → '%s'\n",
                    oldStaffSchedule.getStaffRole(), newStaffSchedule.getStaffRole()));
        }
        if (!java.util.Objects.equals(oldStaffSchedule.getAvailableDate(), newStaffSchedule.getAvailableDate())) {
            detail.append(String.format("Ngày trực: '%s' → '%s'\n",
                    oldStaffSchedule.getAvailableDate(), newStaffSchedule.getAvailableDate()));
        }
        if (!java.util.Objects.equals(oldStaffSchedule.getStartTime(), newStaffSchedule.getStartTime())) {
            detail.append(String.format("Giờ bắt đầu: '%s' → '%s'\n",
                    oldStaffSchedule.getStartTime(), newStaffSchedule.getStartTime()));
        }
        if (!java.util.Objects.equals(oldStaffSchedule.getEndTime(), newStaffSchedule.getEndTime())) {
            detail.append(String.format("Giờ kết thúc: '%s' → '%s'\n",
                    oldStaffSchedule.getEndTime(), newStaffSchedule.getEndTime()));
        }

        if (detail.length() == 0) {
            detail.append("Không có thay đổi.");
        }

        log.setLogDetail(detail.toString());
        staffScheduleLogRepository.save(log);
    }



    @Override
    public List<StaffScheduleLog> getLogsByStaffSchedule(Long staffScheduleId) {
        return staffScheduleLogRepository.findByStaffScheduleIdOrderByLogTimeAsc(staffScheduleId);
    }

    @Override
    public PageResponse<StaffScheduleLog> getAllLogs(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by("logTime").descending());
        Page<StaffScheduleLog> logPage = staffScheduleLogRepository.findAll(pageable);
        return new PageResponse<>(logPage);
    }

    @Override
    public PageResponse<StaffScheduleLog> searchLogs(LogSearchRequest request, int page, int size) {
        Specification<StaffScheduleLog> spec = LogSpecification.filter(request, "staffScheduleId");
        Page<StaffScheduleLog> logPage = staffScheduleLogRepository.findAll(spec, PageRequest.of(page, size, Sort.by("logTime").descending()));
        return new PageResponse<>(logPage);
    }
}
