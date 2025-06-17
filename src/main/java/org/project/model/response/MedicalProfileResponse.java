package org.project.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicalProfileResponse {
    private Long id;
    private Long patientId;
    private Set<String> allergies;
    private Set<String> chronicDiseases;
}
