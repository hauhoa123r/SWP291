package org.project.converter;

import org.project.config.ModelMapperConfig;
import org.project.entity.ProductEntity;
import org.project.exception.mapping.ErrorMappingException;
import org.project.model.response.ProductRespsonse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProductConverter {

    private ModelMapperConfig modelMapperConfig;

    @Autowired
    public void setModelMapperConfig(ModelMapperConfig modelMapperConfig) {
        this.modelMapperConfig = modelMapperConfig;
    }

    public ProductRespsonse toResponse(ProductEntity productEntity) {
        Optional<ProductRespsonse> productRespsonseOptional = Optional.ofNullable(modelMapperConfig.mapper().map(productEntity, ProductRespsonse.class));
        return productRespsonseOptional.orElseThrow(() -> new ErrorMappingException(ProductEntity.class, ProductRespsonse.class));
    }
}
