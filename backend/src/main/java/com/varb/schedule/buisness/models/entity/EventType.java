package com.varb.schedule.buisness.models.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Accessors(chain = true)
@Entity
public class EventType {
    /**
     * Код события
     *
     * <li> - мобилизация
     * <li> - марш
     * <li> - развёртывание
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long typeId;

    @Column(nullable = false)
    private String typeCode;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String color;
}
