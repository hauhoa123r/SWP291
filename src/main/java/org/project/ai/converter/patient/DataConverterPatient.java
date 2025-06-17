package org.project.ai.converter.patient;

import org.modelmapper.ModelMapper;
import org.project.entity.MedicalRecordEntity;
import org.project.entity.PatientEntity;
import org.project.model.dai.DataUserDAI;
import org.project.model.dai.MedicalRecordData;
import org.project.repository.MedicalRecordRepository;
import org.project.repository.PatientRepository;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DataConverterPatient {
    private final MedicalRecordRepository medicalRecordRepository;
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    public DataConverterPatient(MedicalRecordRepository medicalRecordRepository, ModelMapper modelMapper,
                                PatientRepository patientRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.modelMapper = modelMapper;
        this.patientRepository = patientRepository;
    }



    public String toConverterDataUser(Long userId){

        List<PatientEntity> patientEntities = patientRepository.findAllByUserEntity_Id(userId);

        if(patientEntities == null){
            return "This is a new user, no medical examination with data in the system, please reply as usual.";
        }

        List<DataUserDAI> results = new ArrayList<>();
        if (patientEntities != null) {
            for(PatientEntity patient : patientEntities){
                DataUserDAI dataUser = modelMapper.map(patient, DataUserDAI.class);

                Set<MedicalRecordEntity> medicalRecordEntity = patient.getMedicalRecordEntities();
                Set<MedicalRecordData> medicalRecordData = new HashSet<>();
                    for(MedicalRecordEntity medicalRecord: medicalRecordEntity){
                        MedicalRecordData result = modelMapper.map(medicalRecord, MedicalRecordData.class);
                        result.setAllergies(patient.getMedicalProfileEntity().getAllergies());
                        result.setChronicDiseases(patient.getMedicalProfileEntity().getChronicDiseases());
                        medicalRecordData.add(result);
                }

                dataUser.setListMedicalRecordData(medicalRecordData);

                results.add(dataUser);
            }
        }
        return toConverterDataPatientRecord(results);
    }

    private String toConverterDataPatientRecord(List<DataUserDAI> dataUserDAI){
        StringBuilder results = new StringBuilder();

        for(DataUserDAI data : dataUserDAI){
            results.append("- Name: " + data.getFullName() + "\n");
            results.append("- Age: " + data.getBirthDate() + "\n");
            results.append("- Gender: " + data.getGender() + "\n");
            results.append("- Blood Type: " + data.getBloodType() + "\n");
            if(data.getListMedicalRecordData() != null){
                results.append(toConverterDataMedicalRecord(data.getListMedicalRecordData()));
            }
        }
        return results.toString();
    }
    private String toConverterDataMedicalRecord(Set<MedicalRecordData> medicalRecordData){
        StringBuilder allergen = new StringBuilder("- Allergen: ");
        StringBuilder chronicDiseases = new StringBuilder("- Chronic Diseases: ");
        StringBuilder dischargeDate = new StringBuilder("- Discharge Date: ");
        StringBuilder tretmentPlan = new StringBuilder("- Tretment Plan: ");
        StringBuilder diagnosis = new StringBuilder("- Diagnosis: ");
        StringBuilder outcome = new StringBuilder("- Result: ");
        for(MedicalRecordData medicalRecord : medicalRecordData){
            allergen.append(medicalRecord.getAllergies() + ", ");
            chronicDiseases.append(medicalRecord.getChronicDiseases() + ", ");
            dischargeDate.append(medicalRecord.getDischargeDate() + ", ");
            tretmentPlan.append(medicalRecord.getTreatmentPlan() + ", ");
            diagnosis.append(medicalRecord.getDiagnosis() + ", ");
            outcome.append(medicalRecord.getOutcome() + ", ");
        }
        return allergen.toString() + "\n"
                + chronicDiseases.toString() + "\n"
                + dischargeDate.toString() + "\n"
                + tretmentPlan.toString() + "\n"
                + diagnosis.toString() + "\n"
                + outcome.toString();
    }

    private String dataResult(Long userId){
        return """
                This is contains information of patients:
                %s
                """.formatted(toConverterDataUser(userId));
    }
}
