package com.varb.schedule.buisness.logic.service;

import com.varb.schedule.buisness.logic.repository.CalendarRepository;
import com.varb.schedule.buisness.models.dto.CalendarBaseDto;
import com.varb.schedule.buisness.models.dto.CalendarBaseReqDto;
import com.varb.schedule.buisness.models.entity.Calendar;
import com.varb.schedule.config.modelmapper.ModelMapperCustomize;
import com.varb.schedule.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    //TODO Remove it when api will support multiple calendars
    @Override
    public void delete(Long calendarId) {
        if (calendarId == 1L)
            throw new ServiceException("Until you can delete the calendar with calendarId = 1", HttpStatus.FORBIDDEN);
        super.delete(calendarId);
    }

    @Override
    protected String notFindMessage(Long calendarId) {
        return "Не существует календаря (calendarId=" + calendarId + ")";
    }
}
