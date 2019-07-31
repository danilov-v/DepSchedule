package com.varb.schedule.buisness.logic.repository;

import com.varb.schedule.buisness.models.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface UnitRepository extends JpaRepository<Unit, Long> {
    @Query("select u from Unit u left join fetch u.events e " +
            "where e.dateFrom >= :dateFrom and (:dateTo is null or e.dateTo <= :dateTo)")
    List<Unit> findAllWithEvents(LocalDate dateFrom, LocalDate dateTo);
}
