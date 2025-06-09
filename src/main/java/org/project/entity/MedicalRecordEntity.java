package org.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.sql.Date;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "MedicalRecordEntityEntity")
@Table(name = "medical_records", schema = "swp391")
public class MedicalRecordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medical_record_id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientEntity patientEntity;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "appointment_id", nullable = false)
    private AppointmentEntity appointmentEntity;

    @NotNull
    @Column(name = "admission_date", nullable = false)
    private Date admissionDate;

    @NotNull
    @Column(name = "discharge_date", nullable = false)
    private Date dischargeDate;

    @Size(max = 255)
    @NotNull
    @Column(name = "main_complaint", nullable = false)
    private String mainComplaint;

    @NotNull
    @Column(name = "allergies", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> allergies;

    @NotNull
    @Column(name = "chronic_diseases", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> chronicDiseases;

    @NotNull
    @Lob
    @Column(name = "diagnosis", nullable = false)
    private String diagnosis;

    @NotNull
    @Lob
    @Column(name = "treatment_plan", nullable = false)
    private String treatmentPlan;

    @Size(max = 255)
    @Column(name = "outcome")
    private String outcome;

}