package com.varb.schedule.buisness.logic.repository;

import com.varb.schedule.buisness.models.entity.EventDuration;
import com.varb.schedule.buisness.models.entity.EventDurationPK;
import com.varb.schedule.buisness.models.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventDurationRepository extends JpaRepository<EventDuration, EventDurationPK> {
    @Query("select e from EventDuration e where e.compositePK.unitId = :unitId")
    List<EventDuration> findByUnitId(Long unitId);
}
