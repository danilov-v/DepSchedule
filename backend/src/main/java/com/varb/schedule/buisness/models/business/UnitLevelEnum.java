package com.varb.schedule.buisness.models.business;

/**
 * Уровень подразделения
 */
public enum UnitLevelEnum {
    /**
     * Система управления
     */
    CONTROL_SYSTEM(1),

    /**
     * Орган управления
     */
    GOVERNMENT(2),
    /**
     * Пункт управления
     */
    COMMAND_CENTER(3),
    /**
     * Подразделение
     */

    SUBUNIT(4);

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

//    public static UnitLevelEnum fromValue(Integer value) {
//        for (UnitLevelEnum b : UnitLevelEnum.values()) {
//            if (b.value.equals(value)) {
//                return b;
//            }
//        }
//        throw new IllegalArgumentException("Unexpected value '" + value + "'");
//    }
}
