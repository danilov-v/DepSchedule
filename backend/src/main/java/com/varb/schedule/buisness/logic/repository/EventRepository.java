package com.varb.schedule.buisness.logic.repository;

import com.varb.schedule.buisness.models.entity.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("select e from Event e " +
            "where " +
            "e.calendarId = :calendarId and :dateFrom <= e.dateTo and (:dateTo is null or :dateTo >= e.dateFrom)")
    List<Event> findBetweenDates(Long calendarId, LocalDate dateFrom, @Nullable LocalDate dateTo);

    @Query("select e from Event e " +
            "join fetch e.eventType " +
            "join fetch e.unit " +
            "where " +
            "e.calendarId = :calendarId and e.dateTo <= :relativeCurrentDate " +
            "order by e.dateTo desc")

    List<Event> findRecent(Long calendarId, LocalDate relativeCurrentDate, Pageable pageable);

    @Query("select e from Event e " +
            "where " +
            "   e.calendarId = :calendarId and" +
            "   :dateFrom < e.dateTo and :dateTo > e.dateFrom " +
            "   and  e.unit.unitId = :unitId " +
            "   and (:eventId is null or :eventId <> e.eventId)")
    List<Event> findIntersection(Long calendarId, LocalDate dateFrom, LocalDate dateTo, Long unitId, @Nullable Long eventId);
}
