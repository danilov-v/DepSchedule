//package com.varb.schedule.buisness.models.mappers;
//
//import com.varb.schedule.buisness.models.business.UnitNode;
//import com.varb.schedule.buisness.models.dto.EventDurationDto;
//import com.varb.schedule.buisness.models.dto.UnitResponseThreeDto;
//import com.varb.schedule.buisness.models.entity.EventDuration;
//import com.varb.schedule.buisness.models.entity.EventDurationPK;
//import com.varb.schedule.buisness.models.entity.Unit;
//import lombok.RequiredArgsConstructor;
//import org.modelmapper.AbstractConverter;
//import org.modelmapper.ModelMapper;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Component
//@RequiredArgsConstructor
//public class UnitMapper {
//    private final ModelMapper modelMapper;
//
//    @PostConstruct
//    void init() {
//
////        modelMapper.addMappings(new PropertyMap<EventDuration, EventDurationDto>() {
////            protected void configure() {
////                map().setUnitId(source.getCompositePK().getUnitId());
////                map().setEventType(source.getCompositePK().getEventType());
////            }
////        });
//
//        modelMapper.addConverter(new AbstractConverter<List<Unit>, List<UnitResponseThreeDto>>() {
//            @Override
//            protected List<UnitResponseThreeDto> convert(List<Unit> units) {
//                List<UnitNode> unitNodes = units.stream()
//                        .filter(unit -> unit.getUnitLevel() == 1)
//                        .map(unit -> new UnitNode(unit.getUnitId()))
//                        .collect(Collectors.toList());
//
//                units.stream()
//                        .filter(unit -> unit.getUnitLevel() == 2)
//                        .map(unit -> new UnitNode(unit.getUnitId()))
//                        .collect(Collectors.toList());
//
//            }
//        });
//
//
//    }
//
//}
