package org.project.enums;

public enum PaymentMethod {
    CASH("cash"),
    VNPAY("vnpay");


    private final String value;

    PaymentMethod(String value) {
        this.value = value;
    }

    public static void main(String[] args) {
        PaymentMethod paymentMethod = PaymentMethod.CASH;
        System.out.println(paymentMethod.getValue());
    }

    public String getValue() {
        return value;
    }
}
