package org.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "PatientEntityEntity")
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
    @OneToMany
    private Set<MedicalRecordEntity> medicalRecordEntities = new LinkedHashSet<>();

    @ManyToMany
    private Set<PricingPlanEntity> pricingPlanEntities = new LinkedHashSet<>();

    @OneToMany
    private Set<AppointmentEntity> appointmentEntities = new LinkedHashSet<>();
    @OneToMany
    private Set<ReviewEntity> reviewEntities = new LinkedHashSet<>();

/*
 TODO [Reverse Engineering] create field to map the 'relationship' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @ColumnDefault("'SELF'")
    @Column(name = "relationship", columnDefinition = "enum not null")
    private Object relationship;
*/
/*
 TODO [Reverse Engineering] create field to map the 'gender' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @ColumnDefault("'OTHER'")
    @Column(name = "gender", columnDefinition = "enum not null")
    private Object gender;
*/
/*
 TODO [Reverse Engineering] create field to map the 'blood_type' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @Column(name = "blood_type", columnDefinition = "enum")
    private Object bloodType;
*/
}