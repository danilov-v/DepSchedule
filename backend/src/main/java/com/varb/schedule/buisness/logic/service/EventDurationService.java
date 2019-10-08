package com.varb.schedule.buisness.logic.service;

import com.varb.schedule.buisness.logic.repository.EventDurationRepository;
import com.varb.schedule.buisness.models.dto.EventDurationPutDto;
import com.varb.schedule.buisness.models.entity.EventDuration;
import com.varb.schedule.buisness.models.entity.EventDurationPK;
import com.varb.schedule.config.modelmapper.ModelMapperCustomize;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class EventDurationService extends AbstractService<EventDuration, EventDurationPK>{
    private final EventDurationRepository eventDurationRepository;
    private final ModelMapperCustomize modelMapper;
    private final UnitService unitService;
    private final EventTypeService eventTypeService;

    /**
     * Изменяем длительность поумолчанию для событий в подразделении {@code unitId} и типом {@code eventTypeId}
     *
     * @param unitId Id подразделения
     * @param eventTypeId Id типа события
     */
    public EventDuration merge(Long unitId, Long eventTypeId, EventDurationPutDto eventDurationPutDto) {
        checkConsistency(unitId, eventTypeId);
        return merge(new EventDurationPK(unitId, eventTypeId), eventDurationPutDto);
    }

    public void delete(Long unitId, Long eventTypeId) {
        super.delete(new EventDurationPK(unitId, eventTypeId));
    }


    private void checkConsistency(Long unitId, Long eventTypeId) {
        unitService.checkExists(unitId);
        eventTypeService.checkExists(eventTypeId);
    }

    public List<EventDuration> getAll(@Nullable Long unitId) {
        if (unitId == null)
            return super.findAll();
        else
            return getByUnitId(unitId);
    }

    public List<EventDuration> getByUnitId(Long unitId) {
        return eventDurationRepository.findByUnitId(unitId);
    }

    public EventDuration findById(Long unitId, Long eventTypeId) {
        return super.findById(new EventDurationPK(unitId, eventTypeId));
    }

    @Override
    protected String notFindMessage(EventDurationPK eventDurationPK) {
        return "Не найден EventDuration" +
                "(unitId=" + eventDurationPK.getUnitId() + ", eventTypeId=" + eventDurationPK.getEventTypeId() + ")";
    }
}
