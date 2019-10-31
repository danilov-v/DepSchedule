package com.varb.schedule.buisness.logic.repository;

import com.varb.schedule.buisness.models.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long> {

    @Deprecated
    @Modifying(clearAutomatically = true)
    @Query("update Calendar c set " +
            "c.dateFrom = (select min(e.dateFrom) from Event e where e.calendarId = :calendarId), " +
            "c.dateTo =(select max(e.dateTo) from Event e where e.calendarId = :calendarId) " +
            "where c.calendarId = :calendarId")
    void updateCalendarFrame(long calendarId);
}
