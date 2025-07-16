package org.project.admin.mapper;

import org.project.admin.dto.request.ServiceFeatureRequest;
import org.project.admin.dto.response.ServiceFeatureResponse;
import org.project.admin.entity.Service;
import org.project.admin.entity.ServiceFeature;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ServiceFeatureMapper {
    ServiceFeatureResponse toResponse(ServiceFeature entity);
    List<ServiceFeatureResponse> toResponseList(List<ServiceFeature> entities);

    @Mapping(target = "serviceFeatureId", ignore = true)
    @Mapping(target = "service", ignore = true)
    ServiceFeature toEntity(ServiceFeatureRequest request);

    default ServiceFeature toEntity(ServiceFeatureRequest request, Service service) {
        ServiceFeature feature = toEntity(request);
        feature.setService(service);
        return feature;
    }
}
