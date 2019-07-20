package com.varb.schedule.buisness.models.entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Accessors(chain = true)
@Entity
public class EventType {
    /**
     * Код события
     *
     * <li>time1 - мобилизация
     * <li>time2 - марш
     * <li>time3 - развёртывание
     * <li>{...}
     */
    @Id
    private String type;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String color;
}
