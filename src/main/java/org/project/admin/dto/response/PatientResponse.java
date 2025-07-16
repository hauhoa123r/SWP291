package org.project.admin.dto.response;

import org.project.admin.enums.patients.BloodType;
import org.project.admin.enums.patients.Gender;
import org.project.admin.enums.patients.Relationship;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientResponse {
    private Long patientId;
    private Long userId;
    private String phoneNumber;
    private String email;
    private String fullName;
    private String avatarUrl;
    private Relationship relationship;
    private String address;
    private Gender gender;
    private LocalDate birthdate;
    private BloodType bloodType;
}
