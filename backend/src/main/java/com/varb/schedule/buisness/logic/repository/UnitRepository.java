package com.varb.schedule.buisness.logic.repository;

import com.varb.schedule.buisness.models.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.lang.Nullable;

import javax.persistence.QueryHint;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface UnitRepository extends JpaRepository<Unit, Long> {
    //https://vladmihalcea.com/jpql-distinct-jpa-hibernate/
    @QueryHints(value = { @QueryHint(name = "hibernate.query.passDistinctThrough", value = "false")})
    @Query(value = "select distinct u from Unit u " +
            "left join fetch u.events e " +
            "left join fetch u.eventDurations d " +
            "where u.calendarId = :calendarId AND" +
            "      (e is NULL OR " +
            "          :dateFrom <= e.dateTo and (:dateTo is null or :dateTo >= e.dateFrom)) "
    )
    Set<Unit> findAllWithChilds(Long calendarId, LocalDate dateFrom, @Nullable LocalDate dateTo);

    @Query(value = "select u from Unit u where u.calendarId = :calendarId")
    List<Unit> findAllByCalendarId(Long calendarId);
}
