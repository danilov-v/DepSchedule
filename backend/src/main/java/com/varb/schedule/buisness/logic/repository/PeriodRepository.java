package com.varb.schedule.buisness.logic.repository;

import com.varb.schedule.buisness.models.entity.Period;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface PeriodRepository extends JpaRepository<Period, Long> {

    //Not used now
    @Query("Select p from Period p where p.name = :name")
    List<Period> findByName(String name);

    //don't use equals (<= or >=) in comparison because
    //the case when a period starts from the date another one finishes is valid
    @Query("Select p from Period p where p.calendarId = :calendarId and :startDate < p.endDate and :endDate > p.startDate")
    List<Period> findIntersections(Long calendarId, LocalDate startDate, LocalDate endDate);

    @Query("Select p from Period p where p.calendarId = :calendarId")
    List<Period> findAllByCalendarId(Long calendarId);
}
