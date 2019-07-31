package com.varb.schedule.buisness.logic.service;

import com.varb.schedule.buisness.logic.repository.EventRepository;
import com.varb.schedule.buisness.models.business.UnitLevelEnum;
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
import java.util.Optional;

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

    public Event addEvent(EventPostDto eventPostDto) {
        Event event = modelMapper.map(eventPostDto, Event.class);
        checkEvent(event);
        return eventRepository.save(event);
    }

    public Event updateEvent(Long eventId, EventPutDto eventPut) {
        Event event = findEventByEventId(eventId);
        modelMapper.map(eventPut, event);
        checkEvent(event);
        return event;
    }

    public void deleteEvent(Long unitId) {
        try {
            eventRepository.deleteById(unitId);
        } catch (EmptyResultDataAccessException ex) {
            log.error("", ex);
            throw notFindException(unitId);
        }

    }

    public List<Event> getAllEventsBetweenDates(LocalDate dateFrom, @Nullable LocalDate dateTo) {
        return eventRepository.findEventByDateFromAfterAndDateToBefore(dateFrom, dateTo);
    }

    private Event findEventByEventId(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> notFindException(eventId));
    }

    private void checkEvent(Event event) {
        Long unitId = event.getUnitId();
        Long eventTypeId = event.getEventTypeId();

        eventTypeService.exists(eventTypeId);

        if (unitService.findUnitByUnitId(unitId).getUnitLevel() < UnitLevelEnum.SUBUNIT.getValue())
            throw new ServiceException("Событие может быть добавлено только к " +
                    "unit(UnitLevel=" + UnitLevelEnum.SUBUNIT.getValue() + ")");

        event.setDateTo(event.getDateFrom()
                .plusDays(eventDurationService
                        .findEventDuration(unitId, eventTypeId)
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
