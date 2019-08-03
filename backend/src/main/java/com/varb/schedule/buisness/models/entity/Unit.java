package com.varb.schedule.buisness.models.entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"events", "eventDurations"})
@ToString(exclude = {"events", "eventDurations"})
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

    @Column(nullable = true)
    private Integer unitLevel;

    @Setter(AccessLevel.PRIVATE)
//    @OneToMany(fetch = FetchType.LAZY)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "unitId")
//    @JoinColumn(name = "unitId")
    private Set<Event> events;

    @Setter(AccessLevel.PRIVATE)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "compositePK.unitId")
//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "compositePK.unitId")
    private Set<EventDuration> eventDurations;
}
