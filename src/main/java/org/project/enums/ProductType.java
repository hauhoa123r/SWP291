package org.project.enums;

public enum ProductType {
    MEDICINE("medicine"),
    SUPPLIES("supplies"),
    TEST_KIT("test_kit");

    private final String value;
    ProductType(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
