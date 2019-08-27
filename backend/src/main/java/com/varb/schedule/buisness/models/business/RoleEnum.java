package com.varb.schedule.buisness.models.business;

import java.util.EnumSet;
import java.util.Set;

import static com.varb.schedule.buisness.models.business.PrivilegeEnum.*;

/**
 * Роли пользователей системы
 */
public enum RoleEnum {
    SUPERUSER(EnumSet.of(BASE, READ, READ_WRITE, SUPER)),
    ADMIN(EnumSet.of(BASE, READ, READ_WRITE)),
    USER(EnumSet.of(BASE, READ));

    /**
     * Привилегии роли
     */
    private EnumSet<PrivilegeEnum> privileges;

    RoleEnum(EnumSet<PrivilegeEnum> privileges) {
        this.privileges = privileges;
    }

    public Set<PrivilegeEnum> getPrivileges() {
        return privileges;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
