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
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class EventDurationService extends AbstractService<EventDuration, EventDurationPK>{
    private final EventDurationRepository eventDurationRepository;
    private final ModelMapperCustomize modelMapper;
    private final UnitService unitService;
    private final EventTypeService eventTypeService;

    public EventDuration merge(Long unitId, Long eventTypeId, EventDurationPutDto eventDurationPutDto) {
        checkConsistency(unitId, eventTypeId);

        EventDuration eventDuration;
        Optional<EventDuration> optionalEventDuration = findByIdOptional(new EventDurationPK(unitId, eventTypeId));

        if (optionalEventDuration.isPresent()) {
            eventDuration = optionalEventDuration.get();
            modelMapper.map(eventDurationPutDto, eventDuration);
        } else {
            eventDuration = modelMapper.map(eventDurationPutDto, EventDuration.class);
            eventDurationRepository.save(eventDuration.setCompositePK(new EventDurationPK(unitId, eventTypeId)));
        }

        return eventDuration;
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
            return super.getAll();
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
    String notFindMessage(EventDurationPK eventDurationPK) {
        return "Не найден EventDuration" +
                "(unitId=" + eventDurationPK.getUnitId() + ", eventTypeId=" + eventDurationPK.getEventTypeId() + ")";
    }
}
