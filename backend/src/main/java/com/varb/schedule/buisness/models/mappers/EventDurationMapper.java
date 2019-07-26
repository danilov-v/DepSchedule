package com.varb.schedule.buisness.models.mappers;

import com.varb.schedule.buisness.models.dto.EventDurationDto;
import com.varb.schedule.buisness.models.entity.EventDuration;
import com.varb.schedule.buisness.models.entity.EventDurationPK;
import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class EventDurationMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    void init() {

//        modelMapper.addMappings(new PropertyMap<EventDuration, EventDurationDto>() {
//            protected void configure() {
//                map().setUnitId(source.getCompositePK().getUnitId());
//                map().setEventType(source.getCompositePK().getEventType());
//            }
//        });

        AbstractConverter<EventDurationDto, EventDurationPK> toCompositePk = new AbstractConverter<>() {
            @Override
            protected EventDurationPK convert(EventDurationDto source) {
                return new EventDurationPK(source.getUnitId(), source.getEventType());
            }
        };

        modelMapper
                .typeMap(EventDurationDto.class, EventDuration.class)
                .addMappings(mapper -> mapper
                        .using(toCompositePk)
                        .map(source -> source, EventDuration::setCompositePK));

        modelMapper
                .typeMap(EventDuration.class, EventDurationDto.class)
                .addMapping(source -> source.getCompositePK().getEventType(), EventDurationDto::setEventType)
                .addMapping(source -> source.getCompositePK().getUnitId(), EventDurationDto::setUnitId);

    }

}
