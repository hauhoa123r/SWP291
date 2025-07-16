package org.project.admin.mapper;


import org.project.admin.dto.request.AppointmentRequest;
import org.project.admin.dto.response.AppointmentResponse;
import org.project.admin.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    @Mapping(source = "doctor.staffId", target = "doctorId")
    @Mapping(source = "doctor.fullName", target = "doctorName")
    @Mapping(source = "patient.patientId", target = "patientId")
    @Mapping(source = "patient.fullName", target = "patientName")
    @Mapping(source = "service.serviceId", target = "serviceId")
    @Mapping(source = "service.features", target = "serviceFeatures")
    @Mapping(source = "schedulingCoordinator.staffId", target = "schedulingCoordinatorId")
    @Mapping(source = "schedulingCoordinator.fullName", target = "schedulingCoordinatorName")
    AppointmentResponse toResponse(Appointment entity);

    List<AppointmentResponse> toResponseList(List<Appointment> entities);

    @Mapping(target = "appointmentId", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "service", ignore = true)
    @Mapping(target = "schedulingCoordinator", ignore = true)
    Appointment toEntity(AppointmentRequest req);

    default Appointment toEntity(AppointmentRequest req, Staff doctor, Patient patient, Service service, Staff coordinator) {
        Appointment appointment = toEntity(req);
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setService(service);
        appointment.setSchedulingCoordinator(coordinator);
        return appointment;
    }
}

