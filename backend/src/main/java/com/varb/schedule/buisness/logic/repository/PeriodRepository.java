package com.varb.schedule.buisness.logic.repository;

import com.varb.schedule.buisness.models.entity.Period;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PeriodRepository extends JpaRepository<Period, Long> {

    //Not used now
    @Query("Select p from Period p where p.name = :name")
    List<Period> findByName(String name);
}
