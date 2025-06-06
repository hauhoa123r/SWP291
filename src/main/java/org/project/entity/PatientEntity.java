package org.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "patients")
public class PatientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id", nullable = false)
    private Long id;

    @Size(max = 255)
    @Column(name = "phone_number")
    private String phoneNumber;

    @Size(max = 255)
    @Column(name = "avatar")
    private String avatar;

    @Size(max = 255)
    @Column(name = "full_name")
    private String fullName;

    @Lob
    @Column(name = "relationship")
    private String relationship;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Lob
    @Column(name = "gender")
    private String gender;

    @Size(max = 255)
    @Column(name = "insurance_number")
    private String insuranceNumber;

    @Size(max = 255)
    @Column(name = "primary_contact")
    private String primaryContact;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "registered_at")
    private Instant registeredAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

}