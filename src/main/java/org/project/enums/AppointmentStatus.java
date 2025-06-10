package org.project.enums;

public enum AppointmentStatus {
    pending("Pending"),
    confirmed("Confirmed"),
    completed("Completed"),
    cancelled("Cancelled"),
    ;

    private final String value;
    AppointmentStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
