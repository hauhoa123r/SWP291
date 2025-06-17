package org.project.enums;

public enum Label {
    NEW("NEW"),
    SALE("SALE"),
    STANDARD("STANDARD");

    private final String value;

    Label(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
