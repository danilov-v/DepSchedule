package com.varb.schedule.buisness.models.business;

/**
 * Привилегии пользователей системы
 */
public enum PrivilegeEnum {
    BASE(Code.BASE),
    READ(Code.READ),
    READ_WRITE(Code.READ_WRITE),
    SUPER(Code.SUPER);

    private static final String ROLE_PREFIX = "ROLE_";

    private String code;

    PrivilegeEnum(String code) {
        this.code = code;
    }


    public static class Code {
        private Code() {
        }

        public static final String BASE = ROLE_PREFIX + "BASE";
        public static final String READ = ROLE_PREFIX + "READ";
        public static final String READ_WRITE = ROLE_PREFIX + "READ_WRITE";
        public static final String SUPER = ROLE_PREFIX + "SUPER";
    }


    @Override
    public String toString() {
        return code;
    }
}