package com.varb.schedule.buisness.logic.service;

import com.varb.schedule.buisness.logic.repository.CalendarRepository;
import com.varb.schedule.buisness.models.dto.CalendarBaseDto;
import com.varb.schedule.buisness.models.dto.CalendarBaseReqDto;
import com.varb.schedule.buisness.models.entity.Calendar;
import com.varb.schedule.config.modelmapper.ModelMapperCustomize;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    protected String notFindMessage(Long calendarId) {
        return "Не существует календаря (calendarId=" + calendarId + ")";
    }
}
