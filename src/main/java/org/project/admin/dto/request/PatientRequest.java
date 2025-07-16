package org.project.admin.dto.request;

import org.project.admin.enums.patients.BloodType;
import org.project.admin.enums.patients.Gender;
import org.project.admin.enums.patients.Relationship;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientRequest {

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^\\+?\\d{10,15}$", message = "Phone number must be valid (10-15 digits, optional '+' sign)")
    private String phoneNumber;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be a valid format")
    private String email;

    @NotBlank(message = "Full name cannot be blank")
    @Size(min = 1, max = 100, message = "Full name must be between 1 and 100 characters")
    private String fullName;

    private String avatarUrl;

    @NotNull(message = "Relationship cannot be null")
    private Relationship relationship;

    @Size(max = 255, message = "Address cannot be longer than 255 characters")
    private String address;

    @NotNull(message = "Gender cannot be null")
    private Gender gender;

    @NotNull(message = "Birthdate cannot be null")
    @Past(message = "Birthdate must be in the past")
    private LocalDate birthdate;

    private BloodType bloodType;
}
