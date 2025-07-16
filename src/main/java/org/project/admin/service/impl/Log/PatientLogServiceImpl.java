package org.project.admin.service.impl.Log;

import org.project.admin.dto.request.LogSearchRequest;
import org.project.admin.dto.response.PatientResponse;
import org.project.admin.entity.Log.PatientLog;
import org.project.admin.entity.Patient;
import org.project.admin.enums.AuditAction;
import org.project.admin.repository.Log.PatientLogRepository;
import org.project.admin.service.Log.PatientLogService;
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
public class PatientLogServiceImpl implements PatientLogService {
    private final PatientLogRepository patientLogRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void logPatientAction(Patient patient, AuditAction action) {
        PatientLog log = new PatientLog();
        log.setPatientId(patient.getPatientId());
        log.setAction(action);
        log.setLogTime(LocalDateTime.now());
        try {
            log.setLogDetail(objectMapper.writeValueAsString(patient));
        } catch (Exception e) {
            log.setLogDetail("Lỗi: " + e.getMessage());
        }
        patientLogRepository.save(log);
    }

    @Override
    public void logPatientUpdateAction(PatientResponse oldPatient, PatientResponse newPatient, AuditAction action) {
        PatientLog log = new PatientLog();
        log.setPatientId(oldPatient.getPatientId());
        log.setAction(action);
        log.setLogTime(LocalDateTime.now());

        StringBuilder detail = new StringBuilder();

        if (!Objects.equals(oldPatient.getUserId(), newPatient.getUserId())) {
            detail.append(String.format("UserId: '%s' → '%s'\n", oldPatient.getUserId(), newPatient.getUserId()));
        }
        if (!Objects.equals(oldPatient.getFullName(), newPatient.getFullName())) {
            detail.append(String.format("Họ và tên: '%s' → '%s'\n", oldPatient.getFullName(), newPatient.getFullName()));
        }
        if (!Objects.equals(oldPatient.getPhoneNumber(), newPatient.getPhoneNumber())) {
            detail.append(String.format("SĐT: '%s' → '%s'\n", oldPatient.getPhoneNumber(), newPatient.getPhoneNumber()));
        }
        if (!Objects.equals(oldPatient.getEmail(), newPatient.getEmail())) {
            detail.append(String.format("Email: '%s' → '%s'\n", oldPatient.getEmail(), newPatient.getEmail()));
        }
        if (!Objects.equals(oldPatient.getAvatarUrl(), newPatient.getAvatarUrl())) {
            detail.append(String.format("Ảnh đại diện: '%s' → '%s'\n", oldPatient.getAvatarUrl(), newPatient.getAvatarUrl()));
        }
        if (!Objects.equals(oldPatient.getRelationship(), newPatient.getRelationship())) {
            detail.append(String.format("Mối quan hệ: '%s' → '%s'\n",
                    oldPatient.getRelationship() != null ? oldPatient.getRelationship().name() : null,
                    newPatient.getRelationship() != null ? newPatient.getRelationship().name() : null));
        }
        if (!Objects.equals(oldPatient.getAddress(), newPatient.getAddress())) {
            detail.append(String.format("Địa chỉ: '%s' → '%s'\n", oldPatient.getAddress(), newPatient.getAddress()));
        }
        if (!Objects.equals(oldPatient.getGender(), newPatient.getGender())) {
            detail.append(String.format("Giới tính: '%s' → '%s'\n",
                    oldPatient.getGender() != null ? oldPatient.getGender().name() : null,
                    newPatient.getGender() != null ? newPatient.getGender().name() : null));
        }
        if (!Objects.equals(oldPatient.getBirthdate(), newPatient.getBirthdate())) {
            detail.append(String.format("Ngày sinh: '%s' → '%s'\n", oldPatient.getBirthdate(), newPatient.getBirthdate()));
        }
        if (!Objects.equals(oldPatient.getBloodType(), newPatient.getBloodType())) {
            detail.append(String.format("Nhóm máu: '%s' → '%s'\n",
                    oldPatient.getBloodType() != null ? oldPatient.getBloodType().name() : null,
                    newPatient.getBloodType() != null ? newPatient.getBloodType().name() : null));
        }

        if (detail.length() == 0) {
            detail.append("Không có thay đổi.");
        }

        log.setLogDetail(detail.toString());
        patientLogRepository.save(log);
    }


    @Override
    public List<PatientLog> getPatientLogs(Long patientId) {
        return patientLogRepository.findByPatientIdOrderByLogTimeAsc(patientId);
    }

    @Override
    public PageResponse<PatientLog> getAllLogs(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by("logTime").descending());
        Page<PatientLog> logPage = patientLogRepository.findAll(pageable);
        return new PageResponse<>(logPage);
    }

    @Override
    public PageResponse<PatientLog> searchLogs(LogSearchRequest request, int page, int size) {
        Specification<PatientLog> spec = LogSpecification.filter(request, "patientId");
        Page<PatientLog> logPage = patientLogRepository.findAll(spec, PageRequest.of(page, size, Sort.by("logTime").descending()));
        return new PageResponse<>(logPage);
    }

}
