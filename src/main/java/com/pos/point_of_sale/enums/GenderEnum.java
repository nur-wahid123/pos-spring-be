package com.pos.point_of_sale.enums;

public enum GenderEnum {
    L("laki-laki"), P("perempuan");
    private String value;

    GenderEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static GenderEnum fromValue(String value) {
        for (GenderEnum gender : values()) {
            if (gender.value.equals(value)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Invalid value for GenderEnum: " + value);
    }
}
