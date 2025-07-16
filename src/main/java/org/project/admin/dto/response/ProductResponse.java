package org.project.admin.dto.response;

import org.project.admin.enums.product.ProductLabel;
import org.project.admin.enums.product.ProductStatus;
import org.project.admin.enums.product.ProductType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponse {
    private Long productId;
    private ProductType productType;
    private String name;
    private String description;
    private BigDecimal price;
    private String unit;
    private ProductStatus productStatus;
    private Integer stockQuantities;
    private String imageUrl;
    private ProductLabel label;
}
