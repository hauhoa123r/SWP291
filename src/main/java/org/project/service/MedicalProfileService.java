package org.project.service;

import org.project.model.dto.MedicalProfileDTO;
import org.project.model.response.MedicalProfileResponse;

import java.util.List;

public interface MedicalProfileService {
    void createMedicalProfile(MedicalProfileDTO medicalProfileDTO);
    MedicalProfileResponse getMedicalProfileByPatientId(Long Id);
    List<MedicalProfileResponse> getAllMedicalProfiles();
    void updateMedicalProfile(Long medicalProfileId,MedicalProfileDTO medicalProfileDTO);
}
