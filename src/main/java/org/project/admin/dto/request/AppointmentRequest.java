package org.project.admin.dto.request;

import org.project.admin.enums.appoinements.AppointmentStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentRequest {
    private Long doctorId;
    private Long patientId;
    private Long serviceId;
    private LocalDateTime startTime;
    private Integer durationMinutes;
    private AppointmentStatus appointmentStatus;
    private Long schedulingCoordinatorId;
}

