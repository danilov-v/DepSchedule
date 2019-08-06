package com.varb.schedule.buisness.logic.repository;

import com.varb.schedule.buisness.models.entity.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventTypeRepository extends JpaRepository<EventType, Long> {
}
