package com.varb.schedule.buisness.logic.service;

import com.varb.schedule.buisness.logic.repository.EventRepository;
import com.varb.schedule.buisness.models.dto.EventDurationPutDto;
import com.varb.schedule.buisness.models.dto.EventPostDto;
import com.varb.schedule.buisness.models.dto.EventPutDto;
import com.varb.schedule.buisness.models.entity.Event;
import com.varb.schedule.config.modelmapper.ModelMapperCustomize;
import com.varb.schedule.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@Slf4j
public class EventService extends AbstractService<Event, Long> {
    private final EventRepository eventRepository;
    private final ModelMapperCustomize modelMapper;
    private final EventTypeService eventTypeService;
    private final EventDurationService eventDurationService;

    private static final String INTERSECTION_OF_EVENTS = "INTERSECTION_OF_EVENTS";

    public EventService(ModelMapperCustomize modelMapper,
                        EventRepository eventRepository, EventTypeService eventTypeService,
                        EventDurationService eventDurationService) {
        super(eventRepository, modelMapper);
        this.eventRepository = eventRepository;
        this.modelMapper = modelMapper;
        this.eventTypeService = eventTypeService;
        this.eventDurationService = eventDurationService;
    }

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

    public List<Event> getAllBetweenDates(LocalDate dateFrom, @Nullable LocalDate dateTo) {
        return eventRepository.findBetweenDates(dateFrom, dateTo);
    }

    private void checkBeforeSave(Event event, @Nullable Integer duration) {
        Long unitId = event.getUnitId();
        Long eventTypeId = event.getEventTypeId();

        eventTypeService.checkExists(eventTypeId);

//        if (unitService.findById(unitId).getUnitLevel() < UnitLevelEnum.SUBUNIT.getValue())
//            throw new ServiceException("Событие может быть добавлено только к " +
//                    "unit(UnitLevel=" + UnitLevelEnum.SUBUNIT.getValue() + ")");

        if (duration != null) {
            assert duration > 0;
            eventDurationService.merge(
                    event.getUnitId(),
                    event.getEventTypeId(),
                    new EventDurationPutDto().duration(duration));
        }

        event.setDateTo(
                event.getDateFrom()
                        .plusDays(eventDurationService
                                .findById(unitId, eventTypeId)
                                .getDuration()));

        if (eventRepository.isIntersection(event.getDateFrom(), event.getDateTo(), event.getUnitId(), event.getEventId())) {
            String message = "Cобытие пересекается с другим событием в данном подразделении";
            throw new ServiceException(message + "(unitId=" + event.getUnitId() + ")", message, INTERSECTION_OF_EVENTS);
        }

    }

    @Override
    ServiceException notFindException(Long eventId) {
        return new ServiceException("Не существует события (eventId=" + eventId + ")");
    }
}
