package com.varb.schedule.buisness.logic.service;

import com.varb.schedule.buisness.logic.repository.CalendarRepository;
import com.varb.schedule.buisness.models.dto.CalendarBaseDto;
import com.varb.schedule.buisness.models.dto.CalendarExtendedReqDto;
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
public class CalendarService extends AbstractService<Calendar, String> {
    private final ModelMapperCustomize modelMapper;
    private final CalendarRepository calendarRepository;

    public Calendar add(CalendarExtendedReqDto calendarDto) {
        Calendar calendar = modelMapper.map(calendarDto, Calendar.class);
        return save(calendar);
    }

    public Calendar update(String name, CalendarBaseDto calendarBaseDto) {
        Calendar calendar = findById(name);

        if (!calendar.getName().equals(calendarBaseDto.getName())) { //Если PrimaryKey изменился
            Calendar oldCalendar = calendar;
            calendar = new Calendar(oldCalendar); //Клонируем старую сущность
            calendarRepository.delete(oldCalendar); //Удаляем старую сущность из бд
        }

        modelMapper.map(calendarBaseDto, calendar);
        return save(calendar); //Сохраняем новую сущность с новым PrimaryKey
    }

    @Override
    protected String notFindMessage(String name) {
        return "Не существует календаря (name=" + name + ")";
    }
}
