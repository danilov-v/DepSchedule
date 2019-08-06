package com.varb.schedule.buisness.logic.repository;

import com.varb.schedule.buisness.models.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    //@Query("select e from Event e where e.dateFrom >= :dateFrom and (:dateTo is null or e.dateTo <= :dateTo)")
    @Query("select e from Event e " +
            "where " +
            "   e.dateTo >= :dateFrom and (:dateTo is null or e.dateFrom<=:dateTo)")
    List<Event> findBetweenDates(LocalDate dateFrom, @Nullable LocalDate dateTo);

    @Query("select e from Event e " +
            "where " +
            "   e.dateTo >= :dateFrom and  e.dateFrom<=:dateTo " +
            "   and  e.unitId = :unitId " +
            "   and (:eventId is null or :eventId <> e.eventId)")
    List<Event> findIntersection(LocalDate dateFrom, LocalDate dateTo, Long unitId, @Nullable Long eventId);
}
