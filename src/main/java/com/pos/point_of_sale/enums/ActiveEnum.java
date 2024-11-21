package com.pos.point_of_sale.enums;

public enum ActiveEnum {
    ACTIVE("ACTIVE"), INACTIVE("INACTIVE");
    private final String value;
    ActiveEnum(String value) {
        this.value = value;
    }
}
