package org.project.enums;

public enum ProductStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private final String value;

    ProductStatus(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
