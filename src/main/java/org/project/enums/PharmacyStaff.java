package org.project.enums;

public enum PharmacyStaff {
    PHARMACIST("pharmacist"),
    CASHIRER("cashier"),
    INVENTORY_MANAGER("inventory_manager"),
    DELIVERY("delivery");

    private final String value;

    PharmacyStaff(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
