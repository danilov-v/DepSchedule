package com.varb.schedule.buisness.models.mappers;

import com.varb.schedule.buisness.logic.service.EventDurationService;
import com.varb.schedule.buisness.models.dto.EventDurationDto;
import com.varb.schedule.buisness.models.dto.EventResponseDto;
import com.varb.schedule.buisness.models.entity.Event;
import com.varb.schedule.buisness.models.entity.EventDuration;
import com.varb.schedule.buisness.models.entity.EventDurationPK;
import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class EventMapper {
    private final ModelMapper modelMapper;
    private final EventDurationService eventDurationService;

    @PostConstruct
    void init() {

        AbstractConverter<Event, LocalDate> calculateDateTo = new AbstractConverter<>() {
            @Override
            protected LocalDate convert(Event source) {
                return calculateDateTo(source);
            }
        };

        modelMapper
                .typeMap(Event.class, EventResponseDto.class)
                .addMappings(mapper -> mapper
                        .using(calculateDateTo)
                        .map(source -> source, EventResponseDto::setDateTo));
    }

    private LocalDate calculateDateTo(Event event) {
        if (event.toString() == null)
            return null;
        EventDuration eventDuration = eventDurationService.findEventDuration(event.getUnitId(), event.getEventType());
        return event.getDateFrom().plusDays(eventDuration.getDuration());

    }

}
