package org.project.admin.service.impl;

import org.project.admin.dto.request.AppointmentRequest;
import org.project.admin.dto.response.AppointmentResponse;
import org.project.admin.entity.Appointment;
import org.project.admin.entity.Patient;
import org.project.admin.entity.Staff;
import org.project.admin.enums.appoinements.AppointmentStatus;
import org.project.admin.mapper.AppointmentMapper;
import org.project.admin.repository.AppointmentRepository;
import org.project.admin.repository.PatientRepository;
import org.project.admin.repository.ServiceRepository;
import org.project.admin.repository.StaffRepository;
import org.project.admin.service.AppointmentService;
import org.project.admin.util.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service("adminAppointmentServiceImpl")
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final StaffRepository staffRepository;
    private final PatientRepository patientRepository;
    private final ServiceRepository serviceRepository;
    private final AppointmentMapper appointmentMapper;
    @Override
    public AppointmentResponse create(AppointmentRequest req) {
        Staff doctor = staffRepository.findById(req.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        Patient patient = patientRepository.findById(req.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        org.project.admin.entity.Service service = serviceRepository.findById(req.getServiceId())
                .orElseThrow(() -> new RuntimeException("Service not found"));
        Staff coordinator = null;
        if (req.getSchedulingCoordinatorId() != null) {
            coordinator = staffRepository.findById(req.getSchedulingCoordinatorId())
                    .orElseThrow(() -> new RuntimeException("Coordinator not found"));
        }
        Appointment entity = appointmentMapper.toEntity(req, doctor, patient, service, coordinator);
        entity = appointmentRepository.save(entity);
        return appointmentMapper.toResponse(entity);
    }
    @Override
    public AppointmentResponse update(Long id, AppointmentRequest req) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        Staff doctor = staffRepository.findById(req.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        Patient patient = patientRepository.findById(req.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        org.project.admin.entity.Service service = serviceRepository.findById(req.getServiceId())
                .orElseThrow(() -> new RuntimeException("Service not found"));
        Staff coordinator = null;
        if (req.getSchedulingCoordinatorId() != null) {
            coordinator = staffRepository.findById(req.getSchedulingCoordinatorId())
                    .orElseThrow(() -> new RuntimeException("Coordinator not found"));
        }
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setService(service);
        appointment.setSchedulingCoordinator(coordinator);
        appointment.setStartTime(req.getStartTime());
        appointment.setDurationMinutes(req.getDurationMinutes());
        appointment.setAppointmentStatus(req.getAppointmentStatus());
        appointment = appointmentRepository.save(appointment);
        return appointmentMapper.toResponse(appointment);
    }
    @Override
    public AppointmentResponse getById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        return appointmentMapper.toResponse(appointment);
    }
    @Override
    public void delete(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new RuntimeException("Appointment not found");
        }
        appointmentRepository.deleteById(id);
    }
    @Override
    public PageResponse<AppointmentResponse> getPage(Pageable pageable) {
        Page<Appointment> page = appointmentRepository.findAll(pageable);
        Page<AppointmentResponse> mappedPage = page.map(appointmentMapper::toResponse);
        return new PageResponse<>(mappedPage);
    }
    @Override
    public PageResponse<AppointmentResponse> getByDoctorId(Long doctorId, Pageable pageable) {
        Page<Appointment> page = appointmentRepository.findByDoctor_StaffId(doctorId, pageable);
        Page<AppointmentResponse> mappedPage = page.map(appointmentMapper::toResponse);
        return new PageResponse<>(mappedPage);
    }
    @Override
    public PageResponse<AppointmentResponse> getByPatientId(Long patientId, Pageable pageable) {
        Page<Appointment> page = appointmentRepository.findByPatient_PatientId(patientId, pageable);
        Page<AppointmentResponse> mappedPage = page.map(appointmentMapper::toResponse);
        return new PageResponse<>(mappedPage);
    }
    @Override
    public PageResponse<AppointmentResponse> getByStatus(String status, Pageable pageable) {
        Page<Appointment> page = appointmentRepository.findByAppointmentStatus(
                AppointmentStatus.valueOf(status), pageable);
        Page<AppointmentResponse> mappedPage = page.map(appointmentMapper::toResponse);
        return new PageResponse<>(mappedPage);
    }
    @Override
    public PageResponse<AppointmentResponse> getUpcoming(Pageable pageable) {
        LocalDateTime now = LocalDateTime.now();
        Page<Appointment> page = appointmentRepository.findByStartTimeAfter(now, pageable);
        Page<AppointmentResponse> mappedPage = page.map(appointmentMapper::toResponse);
        return new PageResponse<>(mappedPage);
    }
    @Override
    public PageResponse<AppointmentResponse> getUpcomingByStatus(List<AppointmentStatus> statuses, Pageable pageable) {
        LocalDateTime now = LocalDateTime.now();
        Page<Appointment> page = appointmentRepository.findByStartTimeAfterAndAppointmentStatusIn(now, statuses, pageable);
        Page<AppointmentResponse> mappedPage = page.map(appointmentMapper::toResponse);
        return new PageResponse<>(mappedPage);
    }
    @Override
    public PageResponse<AppointmentResponse> getPendingAppointments(Pageable pageable) {
        Page<Appointment> page = appointmentRepository.findByAppointmentStatus(AppointmentStatus.PENDING, pageable);
        Page<AppointmentResponse> mappedPage = page.map(appointmentMapper::toResponse);
        return new PageResponse<>(mappedPage);
    }
}
