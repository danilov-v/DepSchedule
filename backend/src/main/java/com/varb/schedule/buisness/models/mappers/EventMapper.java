package com.varb.schedule.buisness.models.mappers;

import com.varb.schedule.buisness.models.dto.EventPostDto;
import com.varb.schedule.buisness.models.dto.EventPutDto;
import com.varb.schedule.buisness.models.dto.EventResponseDto;
import com.varb.schedule.buisness.models.dto.LocationDto;
import com.varb.schedule.buisness.models.entity.Event;
import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class EventMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    void init() {

        AbstractConverter<Event, LocationDto> fillLocationDto = new AbstractConverter<>() {
            @Override
            protected LocationDto convert(Event source) {
                return new LocationDto()
                        .name(source.getLocationName())
                        .type(LocationDto.TypeEnum.fromValue(source.getLocationType().toString()));
            }
        };

        modelMapper.typeMap(Event.class, EventResponseDto.class)
                .addMappings(mapper -> mapper
                        .using(fillLocationDto)
                        .map(source -> source, EventResponseDto::setLocation));

        modelMapper.typeMap(EventPostDto.class, Event.class)
                .addMapping(source -> source.getLocation().getName(), Event::setLocationName)
                .addMapping(source -> source.getLocation().getType(), Event::setLocationType);

        modelMapper.typeMap(EventPutDto.class, Event.class)
                .addMapping(source -> source.getLocation().getName(), Event::setLocationName)
                .addMapping(source -> source.getLocation().getType(), Event::setLocationType);
    }
}
