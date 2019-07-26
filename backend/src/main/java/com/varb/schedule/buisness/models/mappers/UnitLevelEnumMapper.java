package com.varb.schedule.buisness.models.mappers;

import com.varb.schedule.buisness.models.business.UnitLevelEnum;
import com.varb.schedule.buisness.models.dto.UnitPostDto;
import com.varb.schedule.buisness.models.dto.UnitPutDto;
import com.varb.schedule.buisness.models.dto.UnitResponseDto;
import com.varb.schedule.buisness.models.entity.Unit;
import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ExpressionMap;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class UnitLevelEnumMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    void init() {

//        AbstractConverter<UnitLevelEnum, Integer> enumToIntConverter = new AbstractConverter<>() {
//            @Override
//            protected Integer convert(UnitLevelEnum source) {
//                return source.getValue();
//            }
//        };
//
//        AbstractConverter<Integer, UnitLevelEnum> intToEnumConverter = new AbstractConverter<>() {
//            @Override
//            protected UnitLevelEnum convert(Integer source) {
//                return UnitLevelEnum.fromValue(source);
//            }
//        };
//
//        modelMapper
//                .typeMap(UnitPutDto.class, Unit.class)
//                .addMappings(mapper -> mapper
//                        .using(intToEnumConverter)
//                        .map(UnitPutDto::getUnitLevel, Unit::setUnitLevelEnum));
//
//        modelMapper
//                .typeMap(UnitPostDto.class, Unit.class)
//                .addMappings(mapper -> mapper
//                        .using(intToEnumConverter)
//                        .map(UnitPostDto::getUnitLevel, Unit::setUnitLevelEnum));
//
//        modelMapper
//                .typeMap(Unit.class, UnitResponseDto.class)
//                .addMappings(mapper -> mapper
//                        .using(enumToIntConverter)
//                        .map(Unit::getUnitLevelEnum, UnitResponseDto::setUnitLevel));

    }

}
