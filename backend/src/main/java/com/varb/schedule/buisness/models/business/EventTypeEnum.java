//package com.varb.schedule.buisness.models.business;
//
///**
// * Тип события
// */
//public enum EventTypeEnum {
//    /**
//     *
//     */
//    MOBILIZATION("mobilization"),
//
//    /**
//     *
//     */
//    MARCH("march"),
//    /**
//     *
//     */
//    DEPLOYMENT("deployment");
//    private String value;
//
//    EventTypeEnum(String value) {
//        this.value = value;
//    }
//
//    public String toString() {
//        return this.value;
//    }
//
//    public static EventTypeEnum fromValue(String value) {
//        for (EventTypeEnum b : EventTypeEnum.values()) {
//            if (b.value.equals(value)) {
//                return b;
//            }
//        }
//        throw new IllegalArgumentException("Unexpected value '" + value + "'");
//    }
//}
