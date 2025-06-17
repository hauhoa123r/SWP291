package org.project.converter;

import org.project.config.ModelMapperConfig;
import org.project.entity.PatientEntity;
import org.project.model.dto.PatientDTO;
import org.project.model.response.PatientResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PatientConverter {

    private ModelMapperConfig modelMapperConfig;

    @Autowired
    public PatientConverter(ModelMapperConfig modelMapper) {
        modelMapperConfig = modelMapper;
    }

    public Optional<PatientEntity> toConvertEntity(PatientDTO patientDTO) {
        if (patientDTO == null) {
            return Optional.empty();
        }
        return Optional.of(modelMapperConfig.mapper().map(patientDTO, PatientEntity.class));
    }

    public Optional<PatientResponse> toConvertResponse(PatientEntity patientEntity) {
        if (patientEntity == null) {
            return Optional.empty();
        }
        return Optional.of(modelMapperConfig.mapper().map(patientEntity, PatientResponse.class));
    }
}

