package com.varb.schedule.buisness.models.entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Accessors(chain = true)
@Entity
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "unit_sq_generator")
    @SequenceGenerator(name = "unit_sq_generator", sequenceName = "UNIT_SQ", allocationSize = 1)
    private Long unitId;

    /**
     * Ссылка на подразделение верхнего уровня (для подразделения первого уровня не указывается!)
     */
    private Long parentId;

    @Column(nullable = false)
    private String title;

    private Integer unitLevel;
}
