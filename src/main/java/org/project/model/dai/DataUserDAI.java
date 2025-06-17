package org.project.model.dai;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DataUserDAI {
    private Long patientId;
    private String bloodType;
    private String gender;
    private String fullName;
    private Date birthDate;
    Set<MedicalRecordData> listMedicalRecordData;
}
