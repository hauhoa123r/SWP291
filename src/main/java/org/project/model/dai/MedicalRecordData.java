package org.project.model.dai;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecordData {
    private String allergies;
    private String chronicDiseases;
    private Date dischargeDate;
    private String treatmentPlan;
    private String diagnosis;
    private String outcome;
}
