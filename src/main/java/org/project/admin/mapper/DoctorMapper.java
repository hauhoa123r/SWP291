package org.project.admin.mapper;

import org.project.admin.dto.request.DoctorRequest;
import org.project.admin.dto.response.DoctorResponse;
import org.project.admin.entity.Doctor;
import org.project.admin.entity.Staff;
import org.project.admin.enums.doctors.DoctorRank;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    @Mapping(target = "doctorId", source = "staffId")
    @Mapping(target = "doctorRank", expression = "java(mapRankLevelToEnum(request.getRankLevel()))")
    Doctor toEntity(DoctorRequest request);

    @Mapping(target = "doctorId", source = "doctor.doctorId")
    @Mapping(target = "doctorRank", source = "doctor.doctorRank")
    @Mapping(target = "fullName", source = "staff.fullName")
    @Mapping(target = "avatarUrl", source = "staff.avatarUrl")
    DoctorResponse toResponse(Doctor doctor, Staff staff);

    default DoctorRank mapRankLevelToEnum(int level) {
        return switch (level) {
            case 1 -> DoctorRank.INTERN;
            case 2 -> DoctorRank.RESIDENT;
            case 3 -> DoctorRank.ATTENDING;
            case 4 -> DoctorRank.SPECIALIST;
            case 5 -> DoctorRank.SENIOR_SPECIALIST;
            case 6 -> DoctorRank.CONSULTANT;
            case 7 -> DoctorRank.CHIEF_PHYSICIAN;
            default -> DoctorRank.INTERN;
        };
    }
}
