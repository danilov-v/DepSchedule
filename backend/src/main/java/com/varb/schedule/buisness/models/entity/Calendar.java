package com.varb.schedule.buisness.models.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Accessors(chain = true)
@Entity
public class Calendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long calendarId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int shift;

    @Column(nullable = false)
    private boolean isAstronomical;

    /**
     * Дата, начиная с которой отображается календарь
     */
    @Column(nullable = false)
    private LocalDate dateFrom;

    /**
     * Дата, до которой отображается календарь
     */
    @Column(nullable = false)
    private LocalDate dateTo;


}
