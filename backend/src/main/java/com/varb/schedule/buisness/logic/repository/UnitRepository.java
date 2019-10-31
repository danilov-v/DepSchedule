package com.varb.schedule.buisness.logic.repository;

import com.varb.schedule.buisness.models.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.List;

public interface UnitRepository extends JpaRepository<Unit, Long> {
    //https://vladmihalcea.com/jpql-distinct-jpa-hibernate/
    @QueryHints(value = {@QueryHint(name = "hibernate.query.passDistinctThrough", value = "false")})
    @Query(value = "select distinct u from Unit u " +
            "left join fetch u.events e " +
            "left join fetch u.eventDurations d " +
            "where u.calendarId = :calendarId"
    )
    List<Unit> findAllWithChilds(Long calendarId);

    List<Unit> findByCalendarId(Long calendarId);
}
