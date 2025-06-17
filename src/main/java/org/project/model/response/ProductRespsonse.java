package org.project.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.project.entity.CategoryEntity;
import org.project.entity.ProductAdditionalInfoEntity;
import org.project.entity.ServiceFeatureEntity;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRespsonse {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private List<CategoryEntity> categoryEntities;
    private List<ServiceFeatureEntity> serviceEntityServiceFeatureEntities;
    private String serviceEntityDepartmentEntityName;
    private List<ProductAdditionalInfoEntity> productAdditionalInfoEntities;
}
