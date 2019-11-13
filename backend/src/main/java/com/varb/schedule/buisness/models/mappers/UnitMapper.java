package com.varb.schedule.buisness.models.mappers;


import com.varb.schedule.buisness.models.dto.UnitResponseTreeDto;
import com.varb.schedule.buisness.models.entity.EventDuration;
import com.varb.schedule.buisness.models.entity.Unit;
import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UnitMapper {
    private final ModelMapper modelMapper;


    @PostConstruct
    void init() {

        AbstractConverter<Set<EventDuration>, Map<String, Integer>> eventDurationConverter = new AbstractConverter<>() {
            @Override
            protected Map<String, Integer> convert(Set<EventDuration> source) {
                return source.stream()
                        .collect(Collectors.toMap(
                                o -> o.getCompositePK().getEventTypeId().toString(),
                                EventDuration::getDuration));
            }
        };

        modelMapper
                .typeMap(Unit.class, UnitResponseTreeDto.class)
                .addMappings(mapper -> mapper
                        .using(eventDurationConverter)
                        .map(Unit::getEventDurations, UnitResponseTreeDto::setEventDuration));

    }

    public List<UnitResponseTreeDto> convertToTree(Collection<Unit> source) {
        List<UnitResponseTreeDto> units = source.stream()
                .map(unit -> modelMapper.map(unit, UnitResponseTreeDto.class))
                .collect(Collectors.toList());

        units.forEach(unit ->
                unit.setChildUnit(
                        units.stream()
                                .filter(u -> unit.getUnitId().equals(u.getParentId()))
                                .collect(Collectors.toList())));

        return units.stream().filter(u -> u.getParentId() == null).collect(Collectors.toList());

    }

}
