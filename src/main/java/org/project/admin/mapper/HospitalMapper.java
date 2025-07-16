package org.project.admin.mapper;

import org.project.admin.dto.request.HospitalRequest;
import org.project.admin.dto.response.HospitalResponse;
import org.project.admin.entity.Hospital;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HospitalMapper {

    HospitalResponse toResponse(Hospital entity);

    List<HospitalResponse> toResponseList(List<Hospital> entities);

    @Mapping(target = "hospitalId", ignore = true)
    Hospital toEntity(HospitalRequest request);
}
