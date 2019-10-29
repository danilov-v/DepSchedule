package com.varb.schedule.buisness.models.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Accessors(chain = true)
@Entity
public class Period {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long periodId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false, updatable = false)
    private Long calendarId;
}

/*
CREATE TABLE "PERIOD"(
    "PERIOD_ID" BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    "NAME" VARCHAR(255) NOT NULL,
    "START_DATE" DATE NOT NULL,
    "END_DATE" DATE NOT NULL
)
select * from period;
*/