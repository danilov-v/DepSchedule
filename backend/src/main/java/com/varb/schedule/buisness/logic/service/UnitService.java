package com.varb.schedule.buisness.logic.service;

import com.varb.schedule.buisness.logic.repository.UnitRepository;
import com.varb.schedule.buisness.models.dto.UnitDto;
import com.varb.schedule.buisness.models.dto.UnitReqDto;
import com.varb.schedule.buisness.models.entity.Unit;
import com.varb.schedule.config.modelmapper.ModelMapperCustomize;
import com.varb.schedule.exception.WebApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UnitService extends AbstractService<Unit, Long> {
    private final UnitRepository unitRepository;
    private final ModelMapperCustomize modelMapper;

    public Unit add(UnitReqDto unitPost) {
        Unit unit = modelMapper.map(unitPost, Unit.class);
        checkParent(unit);
        return save(unit);
    }

    public Unit update(Long unitId, UnitDto unitPut) {
        Unit unit = findById(unitId);
        modelMapper.map(unitPut, unit);
        checkParent(unit);
        return unit;
    }

    public List<Unit> getAllExtended(Long calendarId) {
        return unitRepository.findAllWithChilds(calendarId);
    }

    public List<Unit> findAll(Long calendarId) {
        return unitRepository.findByCalendarId(calendarId);
    }

    private void checkParent(Unit unit) {
        Long parentId = unit.getParentId();

        if (parentId == null)
            return;

        if (parentId == 0L) {
            unit.setParentId(null);
            return;
        }

        if (parentId.equals(unit.getUnitId()))
            throw new WebApiException("Невозможно установить parentId равный unitId");

        Unit parentUnit = findById(parentId);

        //check that calendars are the same
        if (!parentUnit.getCalendarId().equals(unit.getCalendarId())) {
            throw new WebApiException(
                    "Календарь, в котором создано родительское подразделение отличается от текущего: unit.calendarId = "
                            + unit.getCalendarId() + ", parentUnit.calendarId = " + parentUnit.getCalendarId());
        }
    }

    @Override
    protected String notFindMessage(Long unitId) {
        return "Не найдено подразделение (unitId=" + unitId + ")";
    }
}
