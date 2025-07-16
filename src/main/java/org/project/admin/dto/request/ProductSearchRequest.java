package org.project.admin.dto.request;

import org.project.admin.enums.product.ProductLabel;
import org.project.admin.enums.product.ProductStatus;
import org.project.admin.enums.product.ProductType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductSearchRequest {
    private String keyword;         // tìm theo name hoặc description
    private ProductType productType;
    private ProductStatus productStatus;
    private ProductLabel label;
    private BigDecimal priceFrom;
    private BigDecimal priceTo;
    private String unit;
}
