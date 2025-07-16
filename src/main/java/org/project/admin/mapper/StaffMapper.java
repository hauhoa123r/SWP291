package org.project.admin.mapper;

import org.project.admin.dto.request.StaffRequest;
import org.project.admin.dto.response.StaffResponse;
import org.project.admin.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StaffMapper {

    @Mapping(target = "staffId", ignore = true)
    @Mapping(target = "user", source = "userId", qualifiedByName = "mapUser")
    @Mapping(target = "manager", source = "managerId", qualifiedByName = "mapManager")
    @Mapping(target = "department", source = "departmentId", qualifiedByName = "mapDepartment")
    @Mapping(target = "hospital", source = "hospitalId", qualifiedByName = "mapHospital")
    @Mapping(target = "avatarUrl", source = "avatarUrl")
    Staff toEntity(StaffRequest request);

    @Mapping(target = "userId", source = "user.userId")
    @Mapping(target = "staffId", source = "staffId")
    @Mapping(target = "fullName", source = "fullName")
    @Mapping(target = "staffRole", source = "staffRole")
    @Mapping(target = "staffType", source = "staffType")
    @Mapping(target = "rankLevel", source = "rankLevel")
    @Mapping(target = "hireDate", source = "hireDate")
    @Mapping(target = "department", source = "department")
    @Mapping(target = "hospital", source = "hospital")
    @Mapping(target = "avatarUrl", source = "avatarUrl")
    StaffResponse toResponse(Staff staff);

    List<StaffResponse> toResponse(List<Staff> staffs);

    @Named("mapUser")
    static User mapUser(Long id) {
        if (id == null) return null;
        User user = new User();
        user.setUserId(id);
        return user;
    }

    @Named("mapManager")
    static Staff mapManager(Long id) {
        if (id == null) return null;
        Staff manager = new Staff();
        manager.setStaffId(id);
        return manager;
    }

    @Named("mapDepartment")
    static Department mapDepartment(Long id) {
        if (id == null) return null;
        Department d = new Department();
        d.setDepartmentId(id);
        return d;
    }

    @Named("mapHospital")
    static Hospital mapHospital(Long id) {
        if (id == null) return null;
        Hospital h = new Hospital();
        h.setHospitalId(id);
        return h;
    }
}

