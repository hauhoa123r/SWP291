package org.project.ai.converter;

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
public class DataPatientConverter {
    private final MedicalRecordRepository medicalRecordRepository;
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    public DataPatientConverter(MedicalRecordRepository medicalRecordRepository, ModelMapper modelMapper,
                                PatientRepository patientRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.modelMapper = modelMapper;
        this.patientRepository = patientRepository;
    }



    public String toConverterDataUser(Long userId){

        List<PatientEntity> patientEntities = patientRepository.findAllByUserEntity_Id(userId);

        List<DataUserDAI> results = new ArrayList<>();
        if (patientEntities != null) {
            for(PatientEntity patient : patientEntities){
                DataUserDAI dataUser = modelMapper.map(patient, DataUserDAI.class);

                Set<MedicalRecordEntity> medicalRecordEntity = patient.getMedicalRecordEntities();

                Set<MedicalRecordData> medicalRecordData = new HashSet<>();

                if(medicalRecordEntity != null){
                    for(MedicalRecordEntity medicalRecord: medicalRecordEntity){
                        MedicalRecordData result = modelMapper.map(medicalRecord, MedicalRecordData.class);
                        result.setAllergies(patient.getMedicalProfileEntity().getAllergies());
                        result.setChronicDiseases(patient.getMedicalProfileEntity().getChronicDiseases());
                        medicalRecordData.add(result);
                    }
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
            results.append("- Tên: " + data.getFullName() + "\n");
            results.append("- Tuổi: " + data.getBirthDate() + "\n");
            results.append("- Giới tính: " + data.getGender() + "\n");
            results.append("- Loại máu: " + data.getBloodType() + "\n");
            if(data.getListMedicalRecordData() != null){
                results.append(toConverterDataMedicalRecord(data.getListMedicalRecordData()));
            }
        }
        return results.toString();
    }
    private String toConverterDataMedicalRecord(Set<MedicalRecordData> medicalRecordData){
        StringBuilder allergen = new StringBuilder("- Dị ứng: ");
        StringBuilder chronicDiseases = new StringBuilder("- Bệnh án: ");
        StringBuilder dischargeDate = new StringBuilder("- Ngày xuất viện: ");
        StringBuilder tretmentPlan = new StringBuilder("- Kế hoạch điều trị: ");
        StringBuilder diagnosis = new StringBuilder("- Chuẩn đoán bệnh: ");
        StringBuilder outcome = new StringBuilder("- Kết quả: ");
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
                Đây là thông tin của người dùng gồm có:
                %s
                """.formatted(toConverterDataUser(userId));
    }
}
