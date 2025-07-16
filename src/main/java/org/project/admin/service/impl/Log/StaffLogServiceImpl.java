package org.project.admin.service.impl.Log;

import org.project.admin.dto.request.LogSearchRequest;
import org.project.admin.dto.response.DepartmentResponse;
import org.project.admin.dto.response.HospitalResponse;
import org.project.admin.dto.response.StaffResponse;
import org.project.admin.entity.Log.StaffLog;
import org.project.admin.entity.Staff;
import org.project.admin.enums.AuditAction;
import org.project.admin.repository.Log.StaffLogRepository;
import org.project.admin.service.Log.StaffLogService;
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
public class StaffLogServiceImpl implements StaffLogService {
    private final StaffLogRepository staffLogRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void logStaffAction(Staff staff, AuditAction action) {
        StaffLog log = new StaffLog();
        log.setStaffId(staff.getStaffId());
        log.setAction(action);
        log.setLogTime(LocalDateTime.now());
        try {
            log.setLogDetail(objectMapper.writeValueAsString(staff));
        } catch (Exception e) {
            log.setLogDetail("Lỗi: " + e.getMessage());
        }
        staffLogRepository.save(log);
    }

    public void logStaffUpdateAction(StaffResponse oldStaff, StaffResponse newStaff, AuditAction action){
        StaffLog log = new StaffLog();
        log.setStaffId(oldStaff.getStaffId());
        log.setAction(action);
        log.setLogTime(LocalDateTime.now());

        StringBuilder detail = new StringBuilder();

        if (!Objects.equals(oldStaff.getUserId(), newStaff.getUserId())) {
            detail.append(String.format("UserId: '%s' → '%s'\n", oldStaff.getUserId(), newStaff.getUserId()));
        }
        if (!Objects.equals(oldStaff.getStaffId(), newStaff.getStaffId())) {
            detail.append(String.format("StaffId: '%s' → '%s'\n", oldStaff.getStaffId(), newStaff.getStaffId()));
        }
        if (!Objects.equals(oldStaff.getFullName(), newStaff.getFullName())) {
            detail.append(String.format("Họ tên: '%s' → '%s'\n", oldStaff.getFullName(), newStaff.getFullName()));
        }
        if (!Objects.equals(oldStaff.getAvatarUrl(), newStaff.getAvatarUrl())) {
            detail.append(String.format("Ảnh đại diện: '%s' → '%s'\n", oldStaff.getAvatarUrl(), newStaff.getAvatarUrl()));
        }
        if (!Objects.equals(oldStaff.getStaffRole(), newStaff.getStaffRole())) {
            detail.append(String.format("Chức vụ: '%s' → '%s'\n", oldStaff.getStaffRole(), newStaff.getStaffRole()));
        }
        if (!Objects.equals(oldStaff.getStaffType(), newStaff.getStaffType())) {
            detail.append(String.format("Loại nhân viên: '%s' → '%s'\n", oldStaff.getStaffType(), newStaff.getStaffType()));
        }
        if (!Objects.equals(oldStaff.getRankLevel(), newStaff.getRankLevel())) {
            detail.append(String.format("Cấp bậc: '%s' → '%s'\n", oldStaff.getRankLevel(), newStaff.getRankLevel()));
        }
        if (!Objects.equals(oldStaff.getHireDate(), newStaff.getHireDate())) {
            detail.append(String.format("Ngày vào làm: '%s' → '%s'\n", oldStaff.getHireDate(), newStaff.getHireDate()));
        }

        // So sánh department
        DepartmentResponse oldDept = oldStaff.getDepartment();
        DepartmentResponse newDept = newStaff.getDepartment();
        if (!Objects.equals(
                oldDept != null ? oldDept.getDepartmentId() : null,
                newDept != null ? newDept.getDepartmentId() : null)
        ) {
            detail.append(String.format("Phòng ban: '%s' → '%s'\n",
                    oldDept != null ? oldDept.getName() : null,
                    newDept != null ? newDept.getName() : null));
        }

        HospitalResponse oldHosp = oldStaff.getHospital();
        HospitalResponse newHosp = newStaff.getHospital();
        if (!Objects.equals(
                oldHosp != null ? oldHosp.getHospitalId() : null,
                newHosp != null ? newHosp.getHospitalId() : null)
        ) {
            detail.append(String.format("Bệnh viện: '%s' → '%s'\n",
                    oldHosp != null ? oldHosp.getName() : null,
                    newHosp != null ? newHosp.getName() : null));
        }

        if (detail.length() == 0) {
            detail.append("Không có thay đổi.");
        }

        log.setLogDetail(detail.toString());
        staffLogRepository.save(log);
    }

    @Override
    public List<StaffLog> getLogsByStaff(Long staffId) {
        return staffLogRepository.findByStaffIdOrderByLogTimeAsc(staffId);
    }

    @Override
    public PageResponse<StaffLog> getAllLogs(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by("logTime").descending());
        Page<StaffLog> logPage = staffLogRepository.findAll(pageable);
        return new PageResponse<>(logPage);
    }

    @Override
    public PageResponse<StaffLog> searchLogs(LogSearchRequest request, int page, int size) {
        Specification<StaffLog> spec = LogSpecification.filter(request, "staffId");
        Page<StaffLog> logPage = staffLogRepository.findAll(spec, PageRequest.of(page, size, Sort.by("logTime").descending()));
        return new PageResponse<>(logPage);
    }
}
