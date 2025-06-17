package org.project.converter;

import org.project.config.ModelMapperConfig;
import org.project.entity.ReviewEntity;
import org.project.entity.StaffEntity;
import org.project.exception.mapping.ErrorMappingException;
import org.project.model.dto.MakeAppointmentDTO;
import org.project.model.response.StaffResponse;
import org.project.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class StaffConverter {

    private ModelMapperConfig modelMapperConfig;

    private final StaffRepository staffRepository;

    public StaffConverter(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Autowired
    public void setModelMapperConfig(ModelMapperConfig modelMapperConfig) {
        this.modelMapperConfig = modelMapperConfig;
        this.modelMapperConfig.mapper().typeMap(StaffEntity.class, StaffResponse.class).setPostConverter(context -> {
            StaffEntity staffEntity = context.getSource();
            StaffResponse staffResponse = context.getDestination();

            staffResponse.setReviewCount(staffEntity.getReviewEntities().size());
            staffResponse.setAverageRating(staffEntity.getReviewEntities().stream().mapToDouble(ReviewEntity::getRating).average().orElse(0.0));

            return staffResponse;
        });
    }

    public StaffResponse toResponse(StaffEntity staffEntity) {
        Optional<StaffResponse> staffResponseOptional = Optional.ofNullable(modelMapperConfig.mapper().map(staffEntity, StaffResponse.class));
        return staffResponseOptional.orElseThrow(() -> new ErrorMappingException(StaffEntity.class, StaffResponse.class));
    }

    public List<MakeAppointmentDTO> toMakeAppointmentDTO() {
        List<MakeAppointmentDTO> results = new ArrayList<>();
        List<StaffEntity> staffEntities = staffRepository.findAll();
        LocalDateTime now = LocalDateTime.now();

        for (StaffEntity staffEntity : staffEntities) {
            MakeAppointmentDTO makeAppointmentDTO = new MakeAppointmentDTO();
            makeAppointmentDTO.setDoctorName(staffEntity.getFullName());
            makeAppointmentDTO.setHospitalName(staffEntity.getHospitalEntity().getName());
            makeAppointmentDTO.setHospitalAddress(staffEntity.getHospitalEntity().getAddress());
            makeAppointmentDTO.setDepartmentName(staffEntity.getDepartmentEntity().getName());

            // Handle appointment start times
            String startDates = "";
            if (staffEntity.getDoctorEntity() != null && staffEntity.getDoctorEntity().getAppointmentEntities() != null) {
                startDates = staffEntity.getDoctorEntity().getAppointmentEntities()
                        .stream()
                        .filter(i -> i.getStartTime().toLocalDateTime().isAfter(now))
                        .map(i -> i.getStartTime().toLocalDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
                        .collect(Collectors.joining(", "));
            }
            makeAppointmentDTO.setStartDate(startDates);
            results.add(makeAppointmentDTO);
        }
        return results;
    }
    public String toMakeAppointmentDTOString() {
        List<MakeAppointmentDTO> appointmentDTOs = toMakeAppointmentDTO();
        StringBuilder result = new StringBuilder();

        result.append("Available Hospitals:\n");
        appointmentDTOs.stream()
                .map(dto -> String.format("%s (%s)", dto.getHospitalName(), dto.getHospitalAddress()))
                .distinct()
                .forEach(hospital -> result.append("  - ").append(hospital).append("\n"));

        result.append("\nAvailable Departments:\n");
        appointmentDTOs.stream()
                .map(MakeAppointmentDTO::getDepartmentName)
                .distinct()
                .forEach(department -> result.append("  - ").append(department).append("\n"));

        result.append("\nAvailable Doctors:\n");
        appointmentDTOs.stream()
                .filter(dto -> dto.getDoctorName() != null)
                .map(dto -> String.format("%s (%s, %s)", dto.getDoctorName(), dto.getDepartmentName(), dto.getHospitalName()))
                .forEach(doctor -> result.append("  - ").append(doctor).append("\n"));

        result.append("\nExisting Appointments:\n");
        appointmentDTOs.stream()
                .filter(dto -> !dto.getStartDate().isEmpty())
                .map(dto -> String.format("%s with %s at %s (%s)", dto.getStartDate(), dto.getDoctorName(), dto.getHospitalName(), dto.getDepartmentName()))
                .forEach(appointment -> result.append("  - ").append(appointment).append("\n"));

        return result.toString();
    }
}
