package org.project.enums;

public enum StaffRole {
    DOCTOR("Doctor"),
    NURSE("Nurse"),
    COORDINATOR("Coordinator");


    private final String value;
    StaffRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
