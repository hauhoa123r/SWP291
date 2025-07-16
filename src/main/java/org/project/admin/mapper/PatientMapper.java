package org.project.admin.mapper;

import org.project.admin.dto.request.PatientRequest;
import org.project.admin.dto.response.PatientResponse;
import org.project.admin.entity.Patient;
import org.project.admin.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    @Mapping(source = "user.userId", target = "userId")
    PatientResponse toResponse(Patient entity);
    List<PatientResponse> toResponseList(List<Patient> entities);

    @Mapping(target = "patientId", ignore = true)
    @Mapping(target = "user", ignore = true)
    Patient toEntity(PatientRequest request);

    default Patient toEntity(PatientRequest request, User user) {
        Patient patient = toEntity(request);
        patient.setUser(user);
        return patient;
    }
}
