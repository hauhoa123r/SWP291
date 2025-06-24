package org.project.enums;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public enum PaymentStatus {
    PENDING("Pending"),
    SUCCESSED("Success"),
    FAILED("Failed");


    private final String status;


    PaymentStatus(String status) {
        this.status = status;
    }


    public String getStatus() {
        return status;
    }
}
