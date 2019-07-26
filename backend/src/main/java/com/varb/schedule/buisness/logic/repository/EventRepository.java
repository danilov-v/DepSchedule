package com.varb.schedule.buisness.logic.repository;

import com.varb.schedule.buisness.models.entity.Event;
import com.varb.schedule.buisness.models.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
