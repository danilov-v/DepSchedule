package com.varb.schedule.buisness.models.mappers;

import com.varb.schedule.buisness.models.dto.PeriodResponseTreeDto;
import com.varb.schedule.buisness.models.entity.Period;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PeriodMapper {

    private final ModelMapper modelMapper;

    public List<PeriodResponseTreeDto> convertToTree(Collection<Period> source) {
        List<PeriodResponseTreeDto> periods = source.stream()
                .map(period -> modelMapper.map(period, PeriodResponseTreeDto.class))
                .collect(Collectors.toList());

        periods.forEach(period ->
                period.setChildPeriods(
                        periods.stream()
                                .filter(p -> period.getPeriodId().equals(p.getParentId()))
                                .collect(Collectors.toList())));

        return periods.stream().filter(p -> p.getParentId() == null).collect(Collectors.toList());
    }

}
