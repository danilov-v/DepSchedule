package com.varb.schedule.buisness.logic.repository;

import com.varb.schedule.buisness.models.entity.EventDuration;
import com.varb.schedule.buisness.models.entity.EventDurationPK;
import com.varb.schedule.buisness.models.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventDurationRepository extends JpaRepository<EventDuration, EventDurationPK> {
}
