package com.varb.schedule.buisness.models.business;

/**
 * Уровень подразделения
 */
public enum UnitLevelEnum {
    /**
     * Система управления
     */
    LEVEL1(1),

    /**
     * Орган управления
     */
    LEVEL2(2),
    /**
     * Пункт управления
     */
    LEVEL3(3),
    /**
     * Подразделение
     */
    LEVEL4(4);

    private Integer value;

    UnitLevelEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public String toString() {
        return this.name();
    }

    public static UnitLevelEnum fromValue(Integer value) {
        for (UnitLevelEnum b : UnitLevelEnum.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}
