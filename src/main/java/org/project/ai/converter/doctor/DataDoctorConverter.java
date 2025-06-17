package org.project.ai.converter.doctor;

import org.modelmapper.ModelMapper;
import org.project.entity.StaffEntity;
import org.project.enums.StaffRole;
import org.project.model.dai.AnswerInfoDoctorForUser;
import org.project.model.dai.TitleNameDoctor;
import org.project.repository.StaffRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataDoctorConverter {

    private final StaffRepository staffRepository;
    private final ModelMapper modelMapper;

    public DataDoctorConverter(StaffRepository staffRepository,ModelMapper modelMapper) {
        this.staffRepository = staffRepository;
        this.modelMapper = modelMapper;
    }

    public String toGetAllDoctors(String userMessage) {
        List<StaffEntity> staffEntities = staffRepository.findAllByStaffRole(StaffRole.DOCTOR);

        List<AnswerInfoDoctorForUser> answerInfoDoctorForUsers = new ArrayList<>();
        List<TitleNameDoctor> titleNameDoctors = new ArrayList<>();
        for (StaffEntity staff : staffEntities) {
            AnswerInfoDoctorForUser answerInfoDoctorForUser = new AnswerInfoDoctorForUser();

            String[] split = staff.getFullName().trim().split("\\.");
            if(split.length > 1){
                titleNameDoctors.add(new TitleNameDoctor(staff.getId(), split[0].trim()));
                answerInfoDoctorForUser.setNameDoctor(split[1].trim());
            }
            else{
                answerInfoDoctorForUser.setNameDoctor(staff.getFullName().trim());
            }
            answerInfoDoctorForUser.setId(staff.getId());
            answerInfoDoctorForUser.setNameDepartment(staff.getDepartmentEntity().getName());
            answerInfoDoctorForUser.setHospitalName(staff.getHospitalEntity().getName());
            answerInfoDoctorForUsers.add(answerInfoDoctorForUser);
        }
        return toConverterDataDoctor(answerInfoDoctorForUsers,titleNameDoctors, userMessage);
    }

    public String toConverterDataDoctor(List<AnswerInfoDoctorForUser> answerInfoDoctorForUsers,
                                        List<TitleNameDoctor> titleNameDoctors,
                                        String userMessage){
        StringBuilder results = new StringBuilder();

        for(int i = 0; i < titleNameDoctors.size(); i++){
            if(userMessage.contains(answerInfoDoctorForUsers.get(i).getNameDoctor())) {

                if(answerInfoDoctorForUsers.get(i).getId() == titleNameDoctors.get(i).getId()){
                    results.append("Name Doctor: "
                            + titleNameDoctors.get(i).getName() + ". "
                            + answerInfoDoctorForUsers.get(i).getNameDoctor() + "\n");
                }
                else {
                    results.append("Name Doctor: " + answerInfoDoctorForUsers.get(i).getNameDoctor() + "\n");
                }

                results.append("Name Department: " + answerInfoDoctorForUsers.get(i).getNameDepartment() + "\n");
                results.append("Name Hospital: " + answerInfoDoctorForUsers.get(i).getHospitalName());
                results.append("\n");
            }
        }
        return results.toString();
    }
}
