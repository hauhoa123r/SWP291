package org.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "AppointmentEntityEntity")
@Table(name = "appointments", schema = "swp391")
public class AppointmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "doctor_id", nullable = false)
    private DoctorEntity doctorEntity;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientEntity patientEntity;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "service_id", nullable = false)
    private ServiceEntity serviceEntity;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private Timestamp startTime;

    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "scheduling_coordinator_id", nullable = false)
    private SchedulingCoordinatorEntity schedulingCoordinatorEntity;

    @OneToMany
    private Set<IngredientRequestEntity> ingredientRequestEntities = new LinkedHashSet<>();

    @OneToMany
    private Set<MedicalRecordEntity> medicalRecordEntities = new LinkedHashSet<>();

    @OneToMany
    private Set<OrderEntity> orderEntities = new LinkedHashSet<>();
    @OneToMany
    private Set<TestRequestEntity> testRequestEntities = new LinkedHashSet<>();

/*
 TODO [Reverse Engineering] create field to map the 'appointment_status' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @Column(name = "appointment_status", columnDefinition = "enum not null")
    private Object appointmentStatus;
*/
}