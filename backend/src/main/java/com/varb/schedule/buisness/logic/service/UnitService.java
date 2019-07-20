package com.varb.schedule.buisness.logic.service;

import com.varb.schedule.buisness.logic.repository.UnitRepository;
import com.varb.schedule.buisness.models.dto.UnitPostDto;
import com.varb.schedule.buisness.models.dto.UnitPutDto;
import com.varb.schedule.buisness.models.entity.Unit;
import com.varb.schedule.config.ModelMapperCustomize;
import com.varb.schedule.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UnitService {
    private final UnitRepository unitRepository;
    private final ModelMapperCustomize modelMapper;

    public Unit addUnit(UnitPostDto unitPost) {
        Unit unit = modelMapper.map(unitPost, Unit.class);
        checkUnitLevel(unit);
        return unitRepository.save(unit);
    }

    public Unit updateUnit(Long unitId, UnitPutDto unitPut) {
        Unit unit = findUnitByUnitId(unitId);
        checkUnitLevel(unit, unitPut.getUnitLevel());
        modelMapper.map(unitPut, unit);
        return unit;
    }

    public void deleteUnit(Long unitId) {
        try {
            unitRepository.deleteById(unitId);
        } catch (EmptyResultDataAccessException ex) {
            throw new ServiceException(ex, "Не существует подразделения c id=" + unitId, HttpStatus.BAD_REQUEST);
        }

    }

    public List<Unit> getAllUnit() {
        return unitRepository.findAll();
    }

    private Unit findUnitByUnitId(Long unitId) {
        return unitRepository.findById(unitId)
                .orElseThrow(() -> new ServiceException("Не найден unit(unitId=" + unitId + ")"));
    }

    private void checkUnitLevel(Unit unit) {
        checkUnitLevel(unit, unit.getUnitLevelEnum().getValue());
    }

    private void checkUnitLevel(Unit unit, int unitLevel) {
        Unit parent = findUnitByUnitId(unit.getParentId());
        if (parent.getUnitLevelEnum().getValue() >= unitLevel)
            throw new ServiceException("unitLevel должен быть меньше чем у родительской сущности!");
    }

}
