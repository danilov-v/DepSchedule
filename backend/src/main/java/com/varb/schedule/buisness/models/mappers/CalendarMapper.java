package com.varb.schedule.buisness.models.mappers;

import com.varb.schedule.buisness.models.dto.CalendarPostDto;
import com.varb.schedule.buisness.models.dto.CalendarResponseDto;
import com.varb.schedule.buisness.models.entity.Calendar;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class CalendarMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    void init() {

        modelMapper.addMappings(new PropertyMap<Calendar, CalendarResponseDto>() {
            protected void configure() {
                map().setIsAstronomical(source.isAstronomical());
            }
        });

        modelMapper.addMappings(new PropertyMap<CalendarPostDto, Calendar>() {
            protected void configure() {
                map().setAstronomical(source.getIsAstronomical());
            }
        });

    }

}
