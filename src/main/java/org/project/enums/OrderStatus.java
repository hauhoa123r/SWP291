package org.project.enums;

public enum OrderStatus {
    PENDING("pending"),
    FULFILLED("fulfilled"),;

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
