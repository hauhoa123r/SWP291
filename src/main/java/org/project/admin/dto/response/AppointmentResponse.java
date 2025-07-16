package org.project.admin.dto.response;

import org.project.admin.enums.appoinements.AppointmentStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class AppointmentResponse {
    private Long appointmentId;
    private Long doctorId;
    private String doctorName;
    private Long patientId;
    private String patientName;
    private Long serviceId;
    private LocalDateTime startTime;
    private Integer durationMinutes;
    private AppointmentStatus appointmentStatus;
    private Long schedulingCoordinatorId;
    private String schedulingCoordinatorName;
    private List<ServiceFeatureResponse> serviceFeatures;
}

