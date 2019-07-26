package com.varb.schedule.buisness.logic.service;

import com.varb.schedule.buisness.logic.repository.EventRepository;
import com.varb.schedule.buisness.models.dto.EventPostDto;
import com.varb.schedule.buisness.models.dto.EventPutDto;
import com.varb.schedule.buisness.models.dto.EventResponseDto;
import com.varb.schedule.buisness.models.entity.Event;
import com.varb.schedule.buisness.models.entity.EventDuration;
import com.varb.schedule.config.modelmapper.ModelMapperCustomize;
import com.varb.schedule.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EventService {
    private final EventRepository eventRepository;
    private final ModelMapperCustomize modelMapper;
    private final EventDurationService eventDurationService;
    private final EventTypeService eventTypeService;
    private final UnitService unitService;

    public Event addEvent(EventPostDto eventPostDto) {
        Event event = modelMapper.map(eventPostDto, Event.class);
        checkConsistency(event);
        return eventRepository.save(event);
    }

    public Event updateEvent(Long eventId, EventPutDto eventPut) {
        Event event = findEventByEventId(eventId);
        modelMapper.map(eventPut, event);
        checkConsistency(event);
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

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

//    public List<EventResponseDto> getAllEventsDto() {
//        return getAllEvents().stream()
//                .map(event -> modelMapper.map(event, EventResponseDto.class))
//                .map(this::calculateDateTo)
//                .collect(Collectors.toList());
//
//    }

    private Event findEventByEventId(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> notFindException(eventId));
    }

    private void checkConsistency(Event event) {
        unitService.exists(event.getUnitId());
        eventTypeService.exists(event.getEventType());
    }


//    private EventResponseDto calculateDateTo(EventResponseDto response) {
//        EventDuration eventDuration = eventDurationService.findEventDuration(response.getUnitId(), response.getEventType());
//        LocalDate dateTo = response.getDateFrom().plusDays(eventDuration.getDuration());
//        response.setDateTo(dateTo);
//        return response;
//    }

    void exists(Long unitId) {
        if (!eventRepository.existsById(unitId))
            throw notFindException(unitId);
    }

    private ServiceException notFindException(Long eventId) {
        return new ServiceException("Не существует события (eventId=" + eventId + ")");
    }
}
