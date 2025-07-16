package org.project.admin.service;

import org.project.admin.dto.request.PatientRequest;
import org.project.admin.dto.request.PatientSearchRequest;
import org.project.admin.dto.response.PatientResponse;
import org.project.admin.util.PageResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PatientService {
    List<PatientResponse> getAllPatients();
    PageResponse<PatientResponse> getPatientPage(Pageable pageable);
    PatientResponse getPatientById(Long id);
    PatientResponse createPatient(PatientRequest req);
    PatientResponse updatePatient(Long id, PatientRequest req);
    void deletePatient(Long id);
    PageResponse<PatientResponse> searchPatientPage(PatientSearchRequest req, Pageable pageable);

    List<PatientResponse> searchPatientsByName(String name);
}
