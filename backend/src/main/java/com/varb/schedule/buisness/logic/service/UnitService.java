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

    public Unit add(UnitPostDto unitPost) {
        Unit unit = modelMapper.map(unitPost, Unit.class);
        checkParent(unit);
        return unitRepository.save(unit);
    }

    public Unit update(Long unitId, UnitPutDto unitPut) {
        Unit unit = findById(unitId);
        modelMapper.map(unitPut, unit);
        checkParent(unit);
        return unit;
    }

    public void delete(Long unitId) {
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

    Unit findById(Long unitId) {
        return unitRepository.findById(unitId)
                .orElseThrow(() -> notFindException(unitId));
    }

    private void checkParent(Unit unit) {
        Long parentId = unit.getParentId();
        if (parentId == null)
            return;
        if (parentId==0L) {
            unit.setParentId(null);
            return;
        }

        Unit parent = findById(parentId);
//        if (parent.getUnitLevel() >= unit.getUnitLevel())
//            throw new ServiceException("unitLevel должен быть больше чем у родительской сущности!");
    }

    void exists(Long unitId) {
        if (!unitRepository.existsById(unitId))
            throw notFindException(unitId);
    }

    private ServiceException notFindException(Long unitId) {
        return new ServiceException("Не найдено подразделение (unitId=" + unitId + ")");
    }


}
