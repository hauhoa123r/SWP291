package org.project.enums;

public enum Gender {
    male("Male"),
    female("Female"),
    other("Other");
    ;
    private final String value;

    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
