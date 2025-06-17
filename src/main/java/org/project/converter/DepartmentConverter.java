package org.project.converter;

import org.project.config.ModelMapperConfig;
import org.project.entity.DepartmentEntity;
import org.project.exception.mapping.ErrorMappingException;
import org.project.model.response.DepartmentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DepartmentConverter {

    private ModelMapperConfig modelMapperConfig;

    @Autowired
    public void setModelMapperConfig(ModelMapperConfig modelMapperConfig) {
        this.modelMapperConfig = modelMapperConfig;
    }

    public DepartmentResponse toResponse(DepartmentEntity departmentEntity) {
        Optional<DepartmentResponse> departmentResponseOptional = Optional.ofNullable(modelMapperConfig.mapper().map(departmentEntity, DepartmentResponse.class));
        return departmentResponseOptional.orElseThrow(() -> new ErrorMappingException(DepartmentEntity.class, DepartmentResponse.class));
    }
}
