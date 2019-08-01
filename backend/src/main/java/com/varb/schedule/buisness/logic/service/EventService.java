package com.varb.schedule.buisness.logic.service;

import com.varb.schedule.buisness.logic.repository.EventRepository;
import com.varb.schedule.buisness.models.dto.EventDurationPutDto;
import com.varb.schedule.buisness.models.dto.EventPostDto;
import com.varb.schedule.buisness.models.dto.EventPutDto;
import com.varb.schedule.buisness.models.entity.Event;
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
public class EventService {
    private final EventRepository eventRepository;
    private final ModelMapperCustomize modelMapper;
    private final EventTypeService eventTypeService;
    private final EventDurationService eventDurationService;
    private final UnitService unitService;

    public Event add(EventPostDto eventPostDto) {
        Event event = modelMapper.map(eventPostDto, Event.class);
        checkBeforeSave(event, eventPostDto.getDuration());
        return eventRepository.save(event);
    }

    public Event update(Long eventId, EventPutDto eventPut) {
        Event event = findById(eventId);
        modelMapper.map(eventPut, event);
        checkBeforeSave(event, eventPut.getDuration());
        return event;
    }

    public void delete(Long unitId) {
        try {
            eventRepository.deleteById(unitId);
        } catch (EmptyResultDataAccessException ex) {
            log.error("", ex);
            throw notFindException(unitId);
        }

    }

    public List<Event> getAllBetweenDates(LocalDate dateFrom, @Nullable LocalDate dateTo) {
        return eventRepository.findBetweenDates(dateFrom, dateTo);
    }

    private Event findById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> notFindException(eventId));
    }

    private void checkBeforeSave(Event event, @Nullable Integer duration) {
        Long unitId = event.getUnitId();
        Long eventTypeId = event.getEventTypeId();

        eventTypeService.exists(eventTypeId);

//        if (unitService.findById(unitId).getUnitLevel() < UnitLevelEnum.SUBUNIT.getValue())
//            throw new ServiceException("Событие может быть добавлено только к " +
//                    "unit(UnitLevel=" + UnitLevelEnum.SUBUNIT.getValue() + ")");

        if (duration != null)
            eventDurationService.merge(
                    event.getUnitId(),
                    event.getEventTypeId(),
                    new EventDurationPutDto().duration(duration));

        event.setDateTo(event.getDateFrom()
                .plusDays(eventDurationService
                        .findById(unitId, eventTypeId)
                        .getDuration()));

    }


    void exists(Long unitId) {
        if (!eventRepository.existsById(unitId))
            throw notFindException(unitId);
    }

    private ServiceException notFindException(Long eventId) {
        return new ServiceException("Не существует события (eventId=" + eventId + ")");
    }
}
