package org.project.admin.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.project.admin.enums.appoinements.AppointmentStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Getter
@Setter
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Long appointmentId;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Staff doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    @Enumerated(EnumType.STRING)
    @Column(name = "appointment_status")
    private AppointmentStatus appointmentStatus;

    @ManyToOne
    @JoinColumn(name = "scheduling_coordinator_id")
    private Staff schedulingCoordinator;

}

