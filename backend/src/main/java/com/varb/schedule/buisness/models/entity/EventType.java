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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "eventType_sq_generator")
    @SequenceGenerator(name = "eventType_sq_generator", sequenceName = "EVENT_TYPE_SQ", allocationSize = 1)
    private Long typeId;

    @Column(nullable = false)
    private String typeCode;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String color;
}
