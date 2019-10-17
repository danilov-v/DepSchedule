package com.varb.schedule.buisness.models.entity;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"events", "eventDurations"})
@ToString(exclude = {"events", "eventDurations"})
@Accessors(chain = true)
@Entity
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long unitId;

    /**
     * Ссылка на подразделение верхнего уровня (для подразделения первого уровня не указывается!)
     */
    @Nullable
    private Long parentId;

    @Column(nullable = false)
    private String title;

    @Nullable
    private String flag;

    @Column(nullable = false)
    private Boolean planned;

//    @Nullable
//    private Integer unitLevel;

    @Setter(AccessLevel.PRIVATE)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "unit", cascade = CascadeType.REMOVE)
    private Set<Event> events;

    @Setter(AccessLevel.PRIVATE)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "compositePK.unitId", cascade = CascadeType.REMOVE)
    private Set<EventDuration> eventDurations;

    @Column(nullable = false, updatable = false)
    private Long calendarId;
}
