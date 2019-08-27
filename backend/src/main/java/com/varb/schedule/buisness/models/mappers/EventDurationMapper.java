package com.varb.schedule.buisness.models.mappers;

import com.varb.schedule.buisness.models.dto.EventDurationResponseDto;
import com.varb.schedule.buisness.models.entity.EventDuration;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class EventDurationMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    void init() {

        modelMapper
                .typeMap(EventDuration.class, EventDurationResponseDto.class)
                .addMapping(source -> source.getCompositePK().getUnitId(), EventDurationResponseDto::setUnitId)
                .addMapping(source -> source.getCompositePK().getEventTypeId(), EventDurationResponseDto::setEventTypeId);

    }

}
