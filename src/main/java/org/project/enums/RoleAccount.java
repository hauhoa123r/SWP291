package org.project.enums;

public enum RoleAccount {
    PATIENT("patient"),
    HOSPITAL_STAFF("hospital_staff"),
    PHARMACY_STAFF("pharmacy_staff");

    private final String name;

    RoleAccount(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
