package org.project.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponse {
    private Long id;
    private Long userId;
    private String fullName;
    private String avatarUrl;
    private String email;
    private String phoneNumber;
    private String address;
    private String gender;
    private String dateOfBirth;
    private String familyRelationship;
    private String bloodType;
}
