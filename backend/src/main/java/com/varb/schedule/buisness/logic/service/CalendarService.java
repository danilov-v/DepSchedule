package com.varb.schedule.buisness.logic.service;

import com.varb.schedule.buisness.logic.repository.CalendarRepository;
import com.varb.schedule.buisness.models.dto.CalendarBaseDto;
import com.varb.schedule.buisness.models.dto.CalendarBaseReqDto;
import com.varb.schedule.buisness.models.entity.Calendar;
import com.varb.schedule.buisness.models.entity.Event;
import com.varb.schedule.config.modelmapper.ModelMapperCustomize;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CalendarService extends AbstractService<Calendar, Long> {
    private final ModelMapperCustomize modelMapper;
    private final CalendarRepository calendarRepository;

    public Calendar add(CalendarBaseReqDto calendarDto) {
        Calendar calendar = modelMapper.map(calendarDto, Calendar.class);
        return save(calendar);
    }

    public Calendar update(long calendarId, CalendarBaseDto calendarDto) {
        Calendar calendar = findById(calendarId);
        modelMapper.map(calendarDto, calendar);
        return calendar;
    }

    /**
     * Update 'dateFrom' and 'dateTo' in {@link Calendar} after delete {@link Event}
     */
    Calendar updateCalendarTimeFrameAfterDeleteEvent(long calendarId, LocalDate dateFrom, LocalDate dateTo) {
        Calendar calendar = findById(calendarId);
        if (dateFrom.equals(calendar.getDateFrom()) || dateTo.equals(calendar.getDateTo())) {
            calendarRepository.updateCalendarFrame(calendarId);
            calendar = findById(calendarId);
        }
        return calendar;
    }

    /**
     * Update 'dateFrom' and 'dateTo' in {@link Calendar} after save {@link Event}
     */
    Calendar updateCalendarTimeFrameAfterSaveEvent(long calendarId, LocalDate dateFrom, LocalDate dateTo) {
        Calendar calendar = findById(calendarId);
        if (calendar.getDateFrom() == null || dateFrom.compareTo(calendar.getDateFrom()) < 0)
            calendar.setDateFrom(dateFrom);
        if (calendar.getDateTo() == null || dateTo.compareTo(calendar.getDateTo()) > 0)
            calendar.setDateTo(dateTo);
        return calendar;
    }

    @Override
    protected String notFindMessage(Long calendarId) {
        return "Не существует календаря (calendarId=" + calendarId + ")";
    }
}
