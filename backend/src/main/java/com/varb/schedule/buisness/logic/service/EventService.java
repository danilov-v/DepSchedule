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
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class EventService extends AbstractService<Event, Long> {
    private final EventRepository eventRepository;
    private final ModelMapperCustomize modelMapper;
    private final EventTypeService eventTypeService;
    private final UnitService unitService;
    private final EventDurationService eventDurationService;
    private final ValidationService validationService;

    public static final String INTERSECTION_OF_EVENTS = "INTERSECTION_OF_EVENTS";
    private static final String DATES_INTERSECTION_MESSAGE = "Данное событие пересекается с уже существующим. " +
            "Проверьте даты его начала и окончания.";


    public Event add(EventPostDto eventPostDto) {
        Event event = modelMapper.map(eventPostDto, Event.class);
        checkBeforeSave(event, eventPostDto.getDuration());
        return save(event);
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

    /**
     * Проверка события перед сохранением
     * @param duration Длительность события переданная в DTO
     * <br>Если событие создавалось с помощью метода POST, {@code duration} != null
     * <br>Если событие изменялось с помощью метода PUT, {@code duration} может быть null
     */
    private void checkBeforeSave(Event event, @Nullable Integer duration) {
        Long unitId = event.getUnitId();
        Long eventTypeId = event.getEventTypeId();

        eventTypeService.checkExists(eventTypeId);
        unitService.checkExists(unitId);

//        if (unitService.findById(unitId).getUnitLevel() < UnitLevelEnum.SUBUNIT.getValue())
//            throw new ServiceException("Событие может быть добавлено только к " +
//                    "unit(UnitLevel=" + UnitLevelEnum.SUBUNIT.getValue() + ")");


        if (duration != null) {
            assert duration > 0;

            eventDurationService.merge(
                    unitId, eventTypeId,
                    new EventDurationPutDto().duration(duration));

            event.setDateTo(
                    event.getDateFrom()
                            .plusDays(duration));
        }

        validationService.checkDates(event.getDateFrom(), event.getDateTo());
        checkIntersection(event);
    }

    private void checkIntersection(Event event) {
        List<Event> eventList = eventRepository.findIntersection(
                event.getDateFrom(), event.getDateTo(), event.getUnitId(), event.getEventId());

        if (!eventList.isEmpty()) {
            String ids = eventList.stream().map(e -> e.getEventId().toString()).collect(Collectors.joining(","));
            throw new ServiceException(
                    "Event you want to add has intersections with other periods with ids: [" + ids + "])",
                    DATES_INTERSECTION_MESSAGE,
                    INTERSECTION_OF_EVENTS);
        }
    }

    @Override
    protected String notFindMessage(Long eventId) {
        return "Не существует события (eventId=" + eventId + ")";
    }
}
