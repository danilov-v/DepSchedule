package com.varb.schedule.buisness.logic.controllers.apiimpl;

import com.varb.schedule.buisness.logic.controllers.api.UnitApi;
import com.varb.schedule.buisness.logic.service.UnitService;
import com.varb.schedule.buisness.models.dto.UnitPostDto;
import com.varb.schedule.buisness.models.dto.UnitPutDto;
import com.varb.schedule.buisness.models.dto.UnitResponseDto;
import com.varb.schedule.buisness.models.entity.Unit;
import com.varb.schedule.config.ModelMapperCustomize;
import com.varb.schedule.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UnitApiImpl implements UnitApi {
    private final UnitService unitService;
    private final ModelMapperCustomize modelMapper;

    @Override
    public ResponseEntity<List<UnitResponseDto>> unitGet() {
        return ResponseEntity.ok(
                modelMapper.mapList(unitService.getAllUnit(), UnitResponseDto.class));
    }

    @Override
    public ResponseEntity<UnitResponseDto> unitPost(@Valid UnitPostDto unitPostDto) {
        return ResponseEntity.ok(
                modelMapper.map(unitService.addUnit(unitPostDto), UnitResponseDto.class));
    }

    @Override
    public ResponseEntity<Void> unitDelete(Long unitId) {
        unitService.deleteUnit(unitId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<UnitResponseDto> unitPut(Long unitId, @Valid UnitPutDto unitPutDto) {
        Unit unit = unitService.updateUnit(unitId, unitPutDto);
        return ResponseEntity.ok(
                modelMapper.map(unit, UnitResponseDto.class));
    }
}
