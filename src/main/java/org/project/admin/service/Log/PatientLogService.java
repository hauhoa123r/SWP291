package org.project.admin.service.Log;

import org.project.admin.dto.request.LogSearchRequest;
import org.project.admin.dto.response.PatientResponse;
import org.project.admin.entity.Log.PatientLog;
import org.project.admin.entity.Patient;
import org.project.admin.enums.AuditAction;
import org.project.admin.util.PageResponse;

import java.util.List;

public interface PatientLogService {
    void logPatientAction(Patient patient, AuditAction action);
    List<PatientLog> getPatientLogs(Long patientId);
    PageResponse<PatientLog> getAllLogs(int page, int size);
    void logPatientUpdateAction(PatientResponse oldPatient, PatientResponse newPatient,AuditAction action);

    PageResponse<PatientLog> searchLogs(LogSearchRequest request, int page, int size);
}

