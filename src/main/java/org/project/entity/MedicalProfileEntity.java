package org.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "medical_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicalProfileEntity {
    @Column(name = "medical_profile_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "allergies", nullable = false)
    private String allergies;

    @NotNull
    @Column(name = "chronic_diseases", nullable = false)
    private String chronicDiseases;

    @NotNull
    @OneToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientEntity patientEntity;
}
