package org.project.admin.dto.request;

import org.project.admin.enums.product.ProductLabel;
import org.project.admin.enums.product.ProductStatus;
import org.project.admin.enums.product.ProductType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {
    @NotNull
    private ProductType productType;

    @NotBlank
    private String name;

    private String description;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal price;

    @NotBlank
    private String unit;

    @NotNull
    private ProductStatus productStatus;

    @NotNull
    @Min(0)
    private Integer stockQuantities;

    private String imageUrl;

    @NotNull
    private ProductLabel label;
}
