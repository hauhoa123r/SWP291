package org.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.project.enums.BloodType;
import org.project.enums.FamilyRelationship;
import org.project.enums.Gender;
import org.project.enums.converter.BloodTypeConverter;

import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "patients", schema = "swp391")
public class PatientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @Size(max = 255)
    @NotNull
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Size(max = 255)
    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Size(max = 255)
    @NotNull
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Size(max = 255)
    @Column(name = "avatar_url")
    private String avatarUrl;

    @Size(max = 255)
    @Column(name = "address")
    private String address;

    @NotNull
    @Column(name = "birthdate", nullable = false)
    private Date birthdate;
    @OneToMany(mappedBy = "patientEntity")
    private Set<MedicalRecordEntity> medicalRecordEntities = new LinkedHashSet<>();

    @ManyToMany
    private Set<PricingPlanEntity> pricingPlanEntities = new LinkedHashSet<>();

    @OneToMany(mappedBy = "patientEntity")
    private Set<AppointmentEntity> appointmentEntities = new LinkedHashSet<>();

    @OneToMany(mappedBy = "patientEntity")
    private Set<ReviewEntity> reviewEntities = new LinkedHashSet<>();

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'SELF'")
    @Column(name = "relationship", columnDefinition = "enum not null")
    private FamilyRelationship relationship;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'OTHER'")
    @Column(name = "gender", columnDefinition = "enum not null")
    private Gender gender;

    @Column(name = "blood_type", columnDefinition = "enum")
    @Convert(converter = BloodTypeConverter.class)
    private BloodType bloodType;

    @OneToOne(mappedBy = "patientEntity")
    private MedicalProfileEntity medicalProfileEntity;
}