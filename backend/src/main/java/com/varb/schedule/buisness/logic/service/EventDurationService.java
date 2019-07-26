package com.varb.schedule.buisness.logic.service;

import com.varb.schedule.buisness.logic.repository.EventDurationRepository;
import com.varb.schedule.buisness.models.dto.EventDurationDto;
import com.varb.schedule.buisness.models.entity.EventDuration;
import com.varb.schedule.buisness.models.entity.EventDurationPK;
import com.varb.schedule.config.modelmapper.ModelMapperCustomize;
import com.varb.schedule.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
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

//    public EventDuration addEventDuration(EventDurationDto eventDurationDto) {
//        EventDuration eventDuration = modelMapper.map(eventDurationDto, EventDuration.class);
//        return eventDurationRepository.save(eventDuration);
//    }

    public EventDuration updateEventDuration(EventDurationDto eventDurationDto) {
        Long unitId = eventDurationDto.getUnitId();
        String eventType = eventDurationDto.getEventType();

        checkConsistency(unitId, eventType);

        EventDuration eventDuration;
        Optional<EventDuration> optionalEventDuration = findOptionalEventDuration(unitId, eventType);

        if (optionalEventDuration.isPresent()) {
            eventDuration = optionalEventDuration.get();
            modelMapper.map(eventDurationDto, eventDuration);
        } else {
            eventDuration = modelMapper.map(eventDurationDto, EventDuration.class);
            eventDurationRepository.save(eventDuration);
        }

        return eventDuration;
    }

    public void deleteEventDuration(Long unitId, String eventType) {

        try {
            eventDurationRepository.deleteById(new EventDurationPK(unitId, eventType));
        } catch (EmptyResultDataAccessException ex) {
            log.error("", ex);
            throw notFindException(unitId, eventType);
        }

    }


    private void checkConsistency(Long unitId, String eventType) {
        unitService.exists(unitId);
        eventTypeService.exists(eventType);
    }

    public List<EventDuration> getAllEventDuration() {
        return eventDurationRepository.findAll();
    }

    private Optional<EventDuration> findOptionalEventDuration(Long unitId, String eventType) {
        return eventDurationRepository.findById(new EventDurationPK(unitId, eventType));
    }

    public EventDuration findEventDuration(Long unitId, String eventType) {
        return eventDurationRepository.findById(new EventDurationPK(unitId, eventType))
                .orElseThrow(() -> notFindException(unitId, eventType));
    }

    void exists(Long unitId, String eventType) {
        if (!eventDurationRepository.existsById(new EventDurationPK(unitId, eventType)))
            throw notFindException(unitId, eventType);
    }

    private ServiceException notFindException(Long unitId, String eventType) {
        return new ServiceException("Не найден EventDuration" +
                "(unitId=" + unitId + ", eventType="+eventType+")");
    }

}
