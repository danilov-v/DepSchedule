package com.varb.schedule.buisness.models.mappers;

import com.varb.schedule.buisness.models.dto.EventResponseDto;
import com.varb.schedule.buisness.models.entity.Event;
import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class EventMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    void init() {

        AbstractConverter<Event, Integer> calculateDateTo = new AbstractConverter<>() {
            @Override
            protected Integer convert(Event source) {
                return Math.toIntExact(ChronoUnit.DAYS.between(source.getDateFrom(), source.getDateTo()));
            }
        };

        modelMapper
                .typeMap(Event.class, EventResponseDto.class)
                .addMappings(mapper -> mapper
                        .using(calculateDateTo)
                        .map(source -> source, EventResponseDto::setDuration));
    }

}
