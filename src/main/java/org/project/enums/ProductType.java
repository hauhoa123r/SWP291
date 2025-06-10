package org.project.enums;

public enum ProductType {
    PRICING_PLAN("PRICING_PLAN"),
    SERVICE("SERVICE"),
    TEST("TEST"),
    MEDICINE("MEDICINE"),
    MEDICAL_PRODUCT("MEDICAL_PRODUCT");
    private final String value;
    ProductType(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
