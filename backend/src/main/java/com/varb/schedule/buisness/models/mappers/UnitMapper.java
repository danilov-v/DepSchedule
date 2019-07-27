package com.varb.schedule.buisness.models.mappers;
import com.varb.schedule.buisness.models.dto.UnitResponseThreeDto;
import com.varb.schedule.buisness.models.entity.Unit;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UnitMapper {
    private final ModelMapper modelMapper;


    public List<UnitResponseThreeDto> convertToThree(List<Unit> source) {
        List<UnitResponseThreeDto> units = source.stream()
                .map(unit -> modelMapper.map(unit, UnitResponseThreeDto.class))
                .collect(Collectors.toList());

        units.forEach(unit ->
                unit.setChildUnit(
                        units.stream()
                                .filter(u -> unit.getUnitId().equals(u.getParentId()))
                                .collect(Collectors.toList())));

        return units.stream().filter(u -> u.getUnitLevel() == 1).collect(Collectors.toList());

    }

}
