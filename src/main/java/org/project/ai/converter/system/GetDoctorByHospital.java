package org.project.ai.converter.system;

import org.project.entity.DoctorEntity;
import org.project.entity.HospitalEntity;
import org.project.entity.StaffEntity;
import org.project.repository.HospitalRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetDoctorByHospital {

    private final HospitalRepository hospitalRepository;

    public GetDoctorByHospital(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    public String toGetAllDoctorByHospital() {
        List<HospitalEntity> hospitalEntity = hospitalRepository.findAll();
        StringBuilder informationDoctor = new StringBuilder();
        for (HospitalEntity hospital : hospitalEntity) {
            System.out.println(hospital.getName());
            System.out.println(hospital.getPhoneNumber());
            System.out.println(hospital.getEmail());
            System.out.println(hospital.getAddress());
            for (StaffEntity doctor : hospital.getStaffEntities()) {
                System.out.println(doctor.getFullName());
                System.out.println(doctor.getRankLevel());
                System.out.println(doctor.getStaffRole());
            }
        }
        return "";
    }
}

