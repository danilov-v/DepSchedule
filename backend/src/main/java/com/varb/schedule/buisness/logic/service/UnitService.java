package com.varb.schedule.buisness.logic.service;

import com.varb.schedule.buisness.logic.repository.UnitRepository;
import com.varb.schedule.buisness.models.dto.UnitPostDto;
import com.varb.schedule.buisness.models.dto.UnitPutDto;
import com.varb.schedule.buisness.models.entity.Unit;
import com.varb.schedule.config.modelmapper.ModelMapperCustomize;
import com.varb.schedule.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
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
        modelMapper.map(unitPut, unit);
        checkUnitLevel(unit);
        return unit;
    }

    public void deleteUnit(Long unitId) {
        try {
            unitRepository.deleteById(unitId);
        } catch (EmptyResultDataAccessException ex) {
            log.error("", ex);
            throw notFindException(unitId);
        }

    }

    public List<Unit> getAllWithEvents(LocalDate dateFrom, @Nullable LocalDate dateTo) {
        return unitRepository.findAllWithEvents(dateFrom, dateTo);
    }

    public List<Unit> getAll() {
        return unitRepository.findAll();
    }

    Unit findUnitByUnitId(Long unitId) {
        return unitRepository.findById(unitId)
                .orElseThrow(() -> notFindException(unitId));
    }

    private void checkUnitLevel(Unit unit) {
        if (unit.getParentId() == null)
            return;
        Unit parent = findUnitByUnitId(unit.getParentId());
        if (parent.getUnitLevel() >= unit.getUnitLevel())
            throw new ServiceException("unitLevel должен быть меньше чем у родительской сущности!");
    }

    private void checkUnitLevel(Unit unit, int unitLevel) {

    }

    void exists(Long unitId) {
        if (!unitRepository.existsById(unitId))
            throw notFindException(unitId);
    }

    private ServiceException notFindException(Long unitId) {
        return new ServiceException("Не найдено подразделение (unitId=" + unitId + ")");
    }


}
