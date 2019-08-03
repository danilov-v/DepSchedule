package com.varb.schedule.buisness.logic.repository;

import com.varb.schedule.buisness.models.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface UnitRepository extends JpaRepository<Unit, Long> {
    @Query(value = "select u from Unit u " +
            "left join fetch u.events e " +
            "left join fetch u.eventDurations d " +
            "where " +
            "      e is NULL OR " +
            "           e.dateTo >= :dateFrom and (:dateTo is null or e.dateFrom <= :dateTo) "
    )
    List<Unit> findAllWithChilds(LocalDate dateFrom, LocalDate dateTo);


//    @Query("select u from Unit u " +
//            "left join fetch u.events e " +
//            "where " +
//            "      e is NULL OR " +
//            "           e.dateTo >= :dateFrom and (:dateTo is null or e.dateFrom <= :dateTo)")
//    List<Unit> findAllWithChilds(LocalDate dateFrom, LocalDate dateTo);
}
