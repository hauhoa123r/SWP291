package org.project.admin.mapper;

import org.project.admin.dto.request.DepartmentRequest;
import org.project.admin.dto.response.DepartmentResponse;
import org.project.admin.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
//    @Mapping(source = "manager.staffId", target = "managerId")
    DepartmentResponse toResponse(Department entity);

    List<DepartmentResponse> toResponseList(List<Department> entities);

    @Mapping(target = "departmentId", ignore = true)
//    @Mapping(target = "manager", ignore = true)
    Department toEntity(DepartmentRequest request);

//    default Department toEntity(DepartmentRequest request, Staff manager) {
//        Department dept = toEntity(request);
//        dept.setManager(manager);
//        return dept;
//    }
}
