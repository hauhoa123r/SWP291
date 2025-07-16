package org.project.admin.dto.request;

import org.project.admin.enums.patients.BloodType;
import org.project.admin.enums.patients.Gender;
import org.project.admin.enums.patients.Relationship;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientSearchRequest {
    private Long userId;
    private Long patientId;
    private String keyword; //Name,PhoneNumber,Email
    private Gender gender;
    private Relationship relationship;
    private BloodType bloodType;
    private LocalDate birthdateFrom;
    private LocalDate birthdateTo;
    private String address;
}

