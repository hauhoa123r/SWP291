package org.project.service;

import org.project.model.dto.PatientDTO;
import org.project.model.response.PatientResponse;

import java.util.List;
import java.util.Optional;

public interface PatientService {
    void createPatient(PatientDTO patientDTO);
    List<PatientResponse> getAllPatients();
    Optional<PatientResponse> getPatientById(Long patientId);
    void updatePatient(Long patientId, PatientDTO patientDTO);
    void deletePatient(Long patientId);
}
