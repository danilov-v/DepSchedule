package com.varb.schedule.buisness.logic.service;

import com.varb.schedule.buisness.logic.repository.UnitRepository;
import com.varb.schedule.buisness.models.dto.UnitPostDto;
import com.varb.schedule.buisness.models.dto.UnitPutDto;
import com.varb.schedule.buisness.models.entity.Unit;
import com.varb.schedule.config.modelmapper.ModelMapperCustomize;
import com.varb.schedule.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;

@Service
@Transactional
@Slf4j
public class UnitService extends AbstractService<Unit, Long> {
    private final UnitRepository unitRepository;
    private final ModelMapperCustomize modelMapper;

    public UnitService(UnitRepository repository, ModelMapperCustomize modelMapper) {
        super(repository, modelMapper);
        this.unitRepository = repository;
        this.modelMapper = modelMapper;
    }

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

    public Set<Unit> getAllExtended(LocalDate dateFrom, @Nullable LocalDate dateTo) {
        return unitRepository.findAllWithChilds(dateFrom, dateTo);
    }

    private void checkParent(Unit unit) {
        Long parentId = unit.getParentId();

        if (parentId == null)
            return;

        if (parentId==0L) {
            unit.setParentId(null);
            return;
        }

        if(parentId.equals(unit.getUnitId()))
            throw new ServiceException("Невозможно установить parentId равное unitId");

        checkExists(parentId);

//        if (parent.getUnitLevel() >= unit.getUnitLevel())
//            throw new ServiceException("unitLevel должен быть больше чем у родительской сущности!");
    }

    @Override
    String notFindMessage(Long unitId) {
        return "Не найдено подразделение (unitId=" + unitId + ")";
    }
}
