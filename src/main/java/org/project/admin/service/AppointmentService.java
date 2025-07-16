package org.project.admin.service;

import org.project.admin.dto.request.AppointmentRequest;
import org.project.admin.dto.response.AppointmentResponse;
import org.project.admin.enums.appoinements.AppointmentStatus;
import org.project.admin.util.PageResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AppointmentService {
    AppointmentResponse create(AppointmentRequest req);
    AppointmentResponse update(Long id, AppointmentRequest req);
    AppointmentResponse getById(Long id);
    void delete(Long id);
    PageResponse<AppointmentResponse> getPage(Pageable pageable);
    PageResponse<AppointmentResponse> getByDoctorId(Long doctorId, Pageable pageable);
    PageResponse<AppointmentResponse> getByPatientId(Long patientId, Pageable pageable);
    PageResponse<AppointmentResponse> getByStatus(String status, Pageable pageable);
    PageResponse<AppointmentResponse> getUpcoming(Pageable pageable);
    PageResponse<AppointmentResponse> getUpcomingByStatus(List<AppointmentStatus> statuses, Pageable pageable);
    PageResponse<AppointmentResponse> getPendingAppointments(Pageable pageable);
}
