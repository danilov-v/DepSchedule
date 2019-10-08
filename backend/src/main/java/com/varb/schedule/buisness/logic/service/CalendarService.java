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
public class CalendarService extends AbstractService<Calendar, String> {
    private final ModelMapperCustomize modelMapper;
    private final CalendarRepository calendarRepository;

    public Calendar add(CalendarBaseReqDto calendarDto) {
        Calendar calendar = modelMapper.map(calendarDto, Calendar.class);
        return save(calendar);
    }

    public Calendar update(String name, CalendarBaseDto calendarBaseDto) {
        Calendar calendar = findById(name);

        if (!calendar.getName().equals(calendarBaseDto.getName())) { //Если PrimaryKey изменился
            delete(name); //Удаляем старую сущность
            calendar = new Calendar(calendar); //Клонируем старую
        }

        modelMapper.map(calendarBaseDto, calendar);
        return save(calendar); //Сохраняем новую сущность с новым PrimaryKey
    }

    @Override
    protected String notFindMessage(String name) {
        return "Не существует календаря (name=" + name + ")";
    }
}
