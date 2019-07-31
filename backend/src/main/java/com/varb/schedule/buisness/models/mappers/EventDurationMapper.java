package com.varb.schedule.buisness.models.mappers;

import com.varb.schedule.buisness.models.dto.EventDurationPutDto;
import com.varb.schedule.buisness.models.entity.EventDuration;
import com.varb.schedule.buisness.models.entity.EventDurationPK;
import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class EventDurationMapper {
    private final ModelMapper modelMapper;

//    @PostConstruct
//    void init() {
//
////        modelMapper.addMappings(new PropertyMap<EventDuration, EventDurationPutDto>() {
////            protected void configure() {
////                map().setUnitId(source.getCompositePK().getUnitId());
////                map().setEventTypeId(source.getCompositePK().getEventTypeId());
////            }
////        });
//
//        AbstractConverter<EventDurationPutDto, EventDurationPK> toCompositePk = new AbstractConverter<>() {
//            @Override
//            protected EventDurationPK convert(EventDurationPutDto source) {
//                return new EventDurationPK(source.getDuration(), source.getEventType());
//            }
//        };
//
//        modelMapper
//                .typeMap(EventDurationPutDto.class, EventDuration.class)
//                .addMappings(mapper -> mapper
//                        .using(toCompositePk)
//                        .map(source -> source, EventDuration::setCompositePK));
//
//        modelMapper
//                .typeMap(EventDuration.class, EventDurationPutDto.class)
//                .addMapping(source -> source.getCompositePK().getEventTypeId(), EventDurationPutDto::setEventType)
//                .addMapping(source -> source.getCompositePK().getUnitId(), EventDurationPutDto::setUnitId);
//
//    }

}
