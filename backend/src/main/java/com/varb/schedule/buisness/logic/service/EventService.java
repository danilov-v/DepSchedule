package com.varb.schedule.buisness.logic.service;

import com.varb.schedule.buisness.logic.repository.EventRepository;
import com.varb.schedule.buisness.models.dto.EventDurationPutDto;
import com.varb.schedule.buisness.models.dto.EventPostDto;
import com.varb.schedule.buisness.models.dto.EventPutDto;
import com.varb.schedule.buisness.models.entity.Calendar;
import com.varb.schedule.buisness.models.entity.Event;
import com.varb.schedule.buisness.models.entity.Unit;
import com.varb.schedule.config.modelmapper.ModelMapperCustomize;
import com.varb.schedule.exception.WebApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    private final CalendarService calendarService;

    public static final String INTERSECTION_OF_EVENTS = "INTERSECTION_OF_EVENTS";
    private static final String DATES_INTERSECTION_MESSAGE = "Данное событие пересекается с уже существующим. " +
            "Проверьте даты его начала и окончания.";


    public Event add(EventPostDto eventPostDto) {
        Event event = modelMapper.map(eventPostDto, Event.class);
        prepareBeforeSave(event);
        return save(event);
    }

    public Event update(Long eventId, EventPutDto eventPut) {
        Event event = findById(eventId);
        modelMapper.map(eventPut, event);
        prepareBeforeSave(event);
        return event;
    }

    public List<Event> getAllBetweenDates(Long calendarId, LocalDate dateFrom, @Nullable LocalDate dateTo) {
        validationService.checkDates(dateFrom, dateTo);
        return eventRepository.findBetweenDates(calendarId, dateFrom, dateTo);
    }

    public List<Event> getRecent(Long calendarId, int count) {
        Calendar calendar = calendarService.findById(calendarId);

        LocalDate relativeCurrentDate;
        if (calendar.isAstronomical())
            relativeCurrentDate = LocalDate.now();
        else
            relativeCurrentDate = LocalDate.now().plusDays(calendar.getShift());

        return eventRepository.findRecent(calendarId, relativeCurrentDate, PageRequest.of(0, count));
    }

    /**
     * Подготовительные операции перед сохранением события
     */
    private void prepareBeforeSave(Event event) {
        checkConsistencyBeforeSave(event);
        updateEventDuration(event);
    }

    /**
     * Проверка согласованности данных
     */
    private void checkConsistencyBeforeSave(Event event) {
        Long unitId = event.getUnitId();
        Long eventTypeId = event.getEventTypeId();
        eventTypeService.checkExists(eventTypeId);
        Unit unit = unitService.checkExists(unitId);
        checkCalendarId(event, unit);

        checkIntersection(event);
    }

    /**
     * Update 'duration' in {@link com.varb.schedule.buisness.models.entity.EventDuration}
     */
    private void updateEventDuration(Event event) {
        int duration = (int) ChronoUnit.DAYS.between(event.getDateFrom(), event.getDateTo());
        eventDurationService.merge(
                event.getUnitId(), event.getEventTypeId(),
                new EventDurationPutDto().duration(duration));
    }

    private void checkIntersection(Event event) {
        List<Event> eventList = eventRepository.findIntersection(
                event.getCalendarId(), event.getDateFrom(), event.getDateTo(), event.getUnitId(), event.getEventId());

        if (!eventList.isEmpty()) {
            String ids = eventList.stream().map(e -> e.getEventId().toString()).collect(Collectors.joining(","));
            throw new WebApiException(
                    "Event you want to add has intersections with other events with ids: [" + ids + "])",
                    DATES_INTERSECTION_MESSAGE,
                    INTERSECTION_OF_EVENTS);
        }
    }

    private void checkCalendarId(Event event, Unit unit) {
        if (!unit.getCalendarId().equals(event.getCalendarId())) {
            throw new WebApiException("Невозможно добавить событие в подразделение из другого календаря: "
            +"event.calendarId = " +event.getCalendarId() +", unit.calendarId = " +unit.getCalendarId());
        }

    }

    @Override
    protected String notFindMessage(Long eventId) {
        return "Не существует события (eventId=" + eventId + ")";
    }
}
