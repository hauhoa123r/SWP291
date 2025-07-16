package org.project.admin.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.project.admin.enums.patients.BloodType;
import org.project.admin.enums.patients.Relationship;
import org.project.enums.Gender;

import java.time.LocalDate;

@Entity
@Table(name = "patients")
@Getter
@Setter

public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private Long patientId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "relationship")
    private Relationship relationship;

    @Column(name = "address")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    @Column(name = "blood_type")
    private BloodType bloodType;

}

