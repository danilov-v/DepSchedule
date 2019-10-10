package com.varb.schedule.buisness.models.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDate;

@Data()
@Accessors(chain = true)
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    @Column(nullable = false)
    private Long eventTypeId;

    @Column(nullable = false)
    private Long unitId;

    @Column(nullable = false)
    private LocalDate dateFrom;

    @Column(nullable = false)
    private LocalDate dateTo;

    @Nullable
    private String note;

}
