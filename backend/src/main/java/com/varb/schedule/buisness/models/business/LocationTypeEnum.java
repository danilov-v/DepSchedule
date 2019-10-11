package com.varb.schedule.buisness.models.business;//package com.varb.schedule.buisness.models.business;

/**
 * Тип события
 */
public enum LocationTypeEnum {
    STATICAL("statical"),

    DISTRICT("district");

    private String value;

    LocationTypeEnum(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }

    public static LocationTypeEnum fromValue(String value) {
        for (LocationTypeEnum b : LocationTypeEnum.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}
