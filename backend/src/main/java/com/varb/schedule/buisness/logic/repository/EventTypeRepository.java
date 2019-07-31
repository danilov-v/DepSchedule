package com.varb.schedule.buisness.logic.repository;

import com.varb.schedule.buisness.models.entity.EventType;
import com.varb.schedule.buisness.models.entity.Unit;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public interface EventTypeRepository extends JpaRepository<EventType, Long> {

}
