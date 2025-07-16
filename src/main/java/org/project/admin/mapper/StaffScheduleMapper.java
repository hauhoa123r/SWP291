package org.project.admin.mapper;

import org.project.admin.dto.request.StaffScheduleRequest;
import org.project.admin.dto.response.StaffScheduleResponse;
import org.project.admin.entity.StaffSchedule;
import org.mapstruct.*;

import java.util.List;
@Mapper(componentModel = "spring")
public interface StaffScheduleMapper {

    // Map từ DTO request sang entity, ignore staff vì sẽ gán ở service
    @Mapping(target = "staff", ignore = true)
    StaffSchedule toEntity(StaffScheduleRequest request);

    // Map entity sang response
    @Mapping(source = "staff.staffId", target = "staffId")
    @Mapping(source = "staff.fullName", target = "staffFullName")
    @Mapping(source = "staff.avatarUrl", target = "staffAvatarUrl")
    @Mapping(target = "staffRole", source = "staff.staffRole")
    StaffScheduleResponse toResponse(StaffSchedule staffSchedule);

    List<StaffScheduleResponse> toResponseList(List<StaffSchedule> schedules);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(StaffScheduleRequest request, @MappingTarget StaffSchedule entity);
}


