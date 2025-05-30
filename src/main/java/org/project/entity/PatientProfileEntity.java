package org.project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "patient_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private Long patientId;


    @Column(name = "insurance_number")
    private String insuranceNumber;

    @Column(name = "primary_contact")
    private String primaryContact;

    @Column(name = "registered_at")
    private LocalDateTime registeredAt;

    @OneToOne
    @JoinColumn(name = "member_id", referencedColumnName = "member_id", nullable = false)
    private FamilyMemberEntity familyMember;
}
