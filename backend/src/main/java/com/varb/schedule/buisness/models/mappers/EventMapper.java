package com.varb.schedule.buisness.models.mappers;

import com.varb.schedule.buisness.models.business.LocationTypeEnum;
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
                LocationDto locationDto = new LocationDto();
                return locationDto.name(source.getLocationName())
                .type(LocationDto.TypeEnum.fromValue(source.getLocationType().toString()));
            }
        };

        AbstractConverter<LocationDto, String> setEventLocationName = new AbstractConverter<>() {
            @Override
            protected String convert(LocationDto source) {
                return source.getName();
            }
        };

        AbstractConverter<LocationDto, LocationTypeEnum> setEventLocationType = new AbstractConverter<>() {
            @Override
            protected LocationTypeEnum convert(LocationDto source) {
                return LocationTypeEnum.fromValue(source.getType().toString());
            }
        };

        modelMapper.typeMap(Event.class, EventResponseDto.class)
                .addMappings(mapper -> mapper
                .using(fillLocationDto)
                .map(source -> source, EventResponseDto::setLocation));

        modelMapper.typeMap(EventPostDto.class, Event.class)
                .addMappings(mapper -> mapper
                        .using(setEventLocationName)
                        .map(EventPostDto::getLocation, Event::setLocationName))
                .addMappings(mapper -> mapper
                        .using(setEventLocationType)
                        .map(EventPostDto::getLocation, Event::setLocationType));

        modelMapper.typeMap(EventPutDto.class, Event.class)
                .addMappings(mapper -> mapper
                        .using(setEventLocationName)
                        .map(EventPutDto::getLocation, Event::setLocationName))
                .addMappings(mapper -> mapper
                        .using(setEventLocationType)
                        .map(EventPutDto::getLocation, Event::setLocationType));
    }
}
