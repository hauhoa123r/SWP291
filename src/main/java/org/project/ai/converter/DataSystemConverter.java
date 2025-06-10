package org.project.ai.converter;

import org.project.entity.HospitalEntity;
import org.project.repository.HospitalRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataSystemConverter {
    private final HospitalRepository hospitalRepository;

    public DataSystemConverter(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    public String toConverterAllHospitals() {
        List<HospitalEntity> results = hospitalRepository.findAll();
        StringBuilder hospitals = new StringBuilder();
        for (HospitalEntity hospital : results) {
            hospitals.append("Name hospital: " + hospital.getName()).append("\n");
            hospitals.append("Address hospital: " + hospital.getAddress()).append("\n");
            hospitals.append("Phone Number: " + hospital.getPhoneNumber()).append("\n");
            hospitals.append("Email address: " + hospital.getEmail()).append("\n");
            hospitals.append("\n");
        }


        return hospitals.toString();
    }
}
