package org.project.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicalProfileDTO {
    private Long patientId;
    private Set<String> allergies;
    private Set<String> chronicDiseases;
}
