package org.project.service.impl;

import org.project.entity.MedicalProfileEntity;
import org.project.entity.PatientEntity;
import org.project.exception.ResourceNotFoundException;
import org.project.model.dto.MedicalProfileDTO;
import org.project.model.response.MedicalProfileResponse;
import org.project.repository.MedicalProfileRepository;
import org.project.repository.PatientRepository;
import org.project.service.MedicalProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MedicalProfileServiceImpl implements MedicalProfileService {

    private PatientRepository patientRepositoryImpl;

    private MedicalProfileRepository medicalProfileRepositoryImpl;

    @Autowired
    public MedicalProfileServiceImpl(PatientRepository patientRepository, MedicalProfileRepository medicalProfileRepository) {
        this.patientRepositoryImpl = patientRepository;
        this.medicalProfileRepositoryImpl = medicalProfileRepository;
    }

    @Override
    public void createMedicalProfile(MedicalProfileDTO medicalProfileDTO) {
        MedicalProfileEntity medicalProfileEntity = new MedicalProfileEntity();

        medicalProfileEntity.setAllergies(medicalProfileDTO.getAllergies().toString());
        medicalProfileEntity.setChronicDiseases(medicalProfileDTO.getChronicDiseases().toString());

        PatientEntity patientEntity = patientRepositoryImpl.findById(medicalProfileDTO.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + medicalProfileDTO.getPatientId()));

        medicalProfileEntity.setPatientEntity(patientEntity);

        medicalProfileRepositoryImpl.save(medicalProfileEntity);
    }

    @Override
    public MedicalProfileResponse getMedicalProfileByPatientId(Long Id) {
        Optional<MedicalProfileEntity> medicalProfileEntityOptional = medicalProfileRepositoryImpl.findByPatientEntity_Id(Id);
        if (medicalProfileEntityOptional != null) {
            MedicalProfileResponse response = new MedicalProfileResponse();
            String allergies = medicalProfileEntityOptional.get().getAllergies();
            String chronicDiseases = medicalProfileEntityOptional.get().getChronicDiseases();

            String[] allergiesArray = allergies.split(",");
            String[] chronicDiseasesArray = chronicDiseases.split(",");

            Set<String> allergiesSet = Set.of(allergiesArray);
            Set<String> chronicDiseasesSet = Set.of(chronicDiseasesArray);

            response.setAllergies(allergiesSet);
            response.setChronicDiseases(chronicDiseasesSet);
            response.setPatientId(medicalProfileEntityOptional.get().getPatientEntity().getId());
            return response;
        }
        throw new ResourceNotFoundException("Medical profile not found for patient with id: " + Id);
    }

    @Override
    public List<MedicalProfileResponse> getAllMedicalProfiles() {
    List<MedicalProfileEntity> medicalProfileEntities = medicalProfileRepositoryImpl.findAll();
        if (medicalProfileEntities != null && !medicalProfileEntities.isEmpty()) {
            return medicalProfileEntities.stream()
                    .map(entity -> {
                        MedicalProfileResponse response = new MedicalProfileResponse();
                        response.setPatientId(entity.getPatientEntity().getId());
                        response.setAllergies(Set.of(entity.getAllergies().split(",")));
                        response.setChronicDiseases(Set.of(entity.getChronicDiseases().split(",")));
                        return response;
                    })
                    .toList();
        }
        throw new ResourceNotFoundException("No medical profiles found.");
    }

    @Override
    public void updateMedicalProfile(Long medicalProfileId, MedicalProfileDTO medicalProfileDTO) {
        Optional<MedicalProfileEntity> medicalProfileEntityOptional = medicalProfileRepositoryImpl.findById(medicalProfileId);
        if (medicalProfileEntityOptional.isPresent()) {
            MedicalProfileEntity medicalProfileEntity = medicalProfileEntityOptional.get();

            medicalProfileEntity.setAllergies(medicalProfileDTO.getAllergies().toString());
            medicalProfileEntity.setChronicDiseases(medicalProfileDTO.getChronicDiseases().toString());

            PatientEntity patientEntity = patientRepositoryImpl.findById(medicalProfileDTO.getPatientId())
                    .orElseThrow(() -> new RuntimeException("Patient not found with id: " + medicalProfileDTO.getPatientId()));

            medicalProfileEntity.setPatientEntity(patientEntity);

            medicalProfileRepositoryImpl.save(medicalProfileEntity);
        } else {
            throw new ResourceNotFoundException("Medical profile not found with id: " + medicalProfileId);
        }
    }
}
