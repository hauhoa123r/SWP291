package org.project.model.response;

import java.math.BigDecimal;

public class CartItemResponse {
    private final String productName;
    private final Integer quantity;
    private final BigDecimal pricePerUnit;
    private final BigDecimal itemTotal;

    public CartItemResponse(String productName, Integer quantity, BigDecimal pricePerUnit) {
        this.productName = productName;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.itemTotal = pricePerUnit.multiply(BigDecimal.valueOf(quantity));
    }

    // Getters (Lombok will automatically generate if you use @Getter)
    public String getProductName() {
        return productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
    }

    public BigDecimal getItemTotal() {
        return itemTotal;
    }
}
