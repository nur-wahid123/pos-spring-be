package com.pos.point_of_sale.enums;

public enum ClassTypeEnum {
    X("X"), XI("XI"), XII("XII");
    private String value;
    ClassTypeEnum(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }

    public static ClassTypeEnum fromValue(String value) {
        for (ClassTypeEnum classType : values()) {
            if (classType.value.equals(value)) {
                return classType;
            }
        }
        throw new IllegalArgumentException("Invalid value for ClassTypeEnum: " + value);
    }
}
