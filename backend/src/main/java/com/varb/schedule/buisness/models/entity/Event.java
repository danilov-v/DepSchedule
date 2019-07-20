package com.varb.schedule.buisness.models.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Accessors(chain = true)
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_sq_generator")
    @SequenceGenerator(name = "event_sq_generator", sequenceName = "EVENT_SQ")
    private Long eventId;

    @Column(nullable = false)
    private String eventType;

    @Column(nullable = false)
    private Long unitId;

    private LocalDate dateFrom;

    private String note;

}
