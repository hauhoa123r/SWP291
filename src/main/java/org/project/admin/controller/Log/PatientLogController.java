package org.project.admin.controller.Log;

import org.project.admin.dto.request.LogSearchRequest;
import org.project.admin.entity.Log.PatientLog;
import org.project.admin.service.Log.PatientLogService;
import org.project.admin.util.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/patients/logs")
@RequiredArgsConstructor
public class PatientLogController {
    private final PatientLogService patientLogService;

    // Lấy toàn bộ log, có phân trang
    @GetMapping
    public PageResponse<PatientLog> getAllLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return patientLogService.getAllLogs(page, size);
    }

    // Lấy log của một bệnh nhân theo id
    @GetMapping("/patient/{patientId}")
    public List<PatientLog> getLogsByPatient(@PathVariable Long patientId) {
        return patientLogService.getPatientLogs(patientId);
    }

    @PostMapping("/search")
    public PageResponse<PatientLog> searchLogs(
            @RequestBody LogSearchRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return patientLogService.searchLogs(request, page, size);
    }
}
