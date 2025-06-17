package org.project.enums;

public enum PaymentStatus {
    PENDING("Pending"),
    SUCCESSED("Success");


    private final String status;


    PaymentStatus(String status) {
        this.status = status;
    }


    public String getStatus() {
        return status;
    }
}
