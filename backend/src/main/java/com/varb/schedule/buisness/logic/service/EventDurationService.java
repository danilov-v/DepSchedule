package com.varb.schedule.buisness.logic.service;

import com.varb.schedule.buisness.logic.repository.EventDurationRepository;
import com.varb.schedule.buisness.models.dto.EventDurationPutDto;
import com.varb.schedule.buisness.models.entity.EventDuration;
import com.varb.schedule.buisness.models.entity.EventDurationPK;
import com.varb.schedule.config.modelmapper.ModelMapperCustomize;
import com.varb.schedule.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EventDurationService {
    private final EventDurationRepository eventDurationRepository;
    private final ModelMapperCustomize modelMapper;
    private final UnitService unitService;
    private final EventTypeService eventTypeService;

//    public EventDuration addEventDuration(EventDurationPutDto eventDurationDto) {
//        EventDuration eventDuration = modelMapper.map(eventDurationDto, EventDuration.class);
//        return eventDurationRepository.save(eventDuration);
//    }

    public EventDuration mergeEventDuration(Long unitId, Long eventTypeId, EventDurationPutDto eventDurationPutDto) {
        checkConsistency(unitId, eventTypeId);

        EventDuration eventDuration;
        Optional<EventDuration> optionalEventDuration = findOptionalEventDuration(unitId, eventTypeId);

        if (optionalEventDuration.isPresent()) {
            eventDuration = optionalEventDuration.get();
            modelMapper.map(eventDurationPutDto, eventDuration);
        } else {
            eventDuration = modelMapper.map(eventDurationPutDto, EventDuration.class);
            eventDurationRepository.save(eventDuration.setCompositePK(new EventDurationPK(unitId, eventTypeId)));
        }

        return eventDuration;
    }

    public void deleteEventDuration(Long unitId, Long eventTypeId) {

        try {
            eventDurationRepository.deleteById(new EventDurationPK(unitId, eventTypeId));
        } catch (EmptyResultDataAccessException ex) {
            log.error("", ex);
            throw notFindException(unitId, eventTypeId);
        }

    }


    private void checkConsistency(Long unitId, Long eventTypeId) {
        unitService.exists(unitId);
        eventTypeService.exists(eventTypeId);
    }

    public List<EventDuration> getAll(@Nullable Long unitId) {
        if (unitId == null)
            return eventDurationRepository.findAll();
        else
            return getByUnitId(unitId);
    }

    public List<EventDuration> getByUnitId(Long unitId) {
        return eventDurationRepository.findByUnitId(unitId);
    }

    private Optional<EventDuration> findOptionalEventDuration(Long unitId, Long eventTypeId) {
        return eventDurationRepository.findById(new EventDurationPK(unitId, eventTypeId));
    }

    public EventDuration findEventDuration(Long unitId, Long eventTypeId) {
        return eventDurationRepository.findById(new EventDurationPK(unitId, eventTypeId))
                .orElseThrow(() -> notFindException(unitId, eventTypeId));
    }

    void exists(Long unitId, Long eventTypeId) {
        if (!eventDurationRepository.existsById(new EventDurationPK(unitId, eventTypeId)))
            throw notFindException(unitId, eventTypeId);
    }

    private ServiceException notFindException(Long unitId, Long eventTypeId) {
        return new ServiceException("Не найден EventDuration" +
                "(unitId=" + unitId + ", eventTypeId=" + eventTypeId + ")");
    }

}
