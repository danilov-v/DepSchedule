package com.varb.schedule.buisness.logic.repository;

import com.varb.schedule.buisness.models.entity.Event;
import com.varb.schedule.buisness.models.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("select e from Event e where e.dateFrom >= :dateFrom and (:dateTo is null or e.dateTo <= :dateTo)")
    List<Event> findEventByDateFromAfterAndDateToBefore(LocalDate dateFrom, LocalDate dateTo);
}
