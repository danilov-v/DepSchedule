package com.varb.schedule.buisness.logic.service;

import com.varb.schedule.buisness.logic.repository.EventTypeRepository;
import com.varb.schedule.buisness.models.dto.EventPostDto;
import com.varb.schedule.buisness.models.dto.EventTypePutDto;
import com.varb.schedule.buisness.models.entity.EventType;
import com.varb.schedule.config.modelmapper.ModelMapperCustomize;
import com.varb.schedule.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EventTypeService {
    private final EventTypeRepository eventTypeRepository;
    private final ModelMapperCustomize modelMapper;
    private final static Function<String, ServiceException> NOT_FIND = (type) -> new ServiceException("Не найден eventType(type=" + type + ")");

    public EventType addEventType(EventPostDto eventPostDto) {
        EventType eventType = modelMapper.map(eventPostDto, EventType.class);
        return eventTypeRepository.save(eventType);
    }

    public EventType updateEventType(String type, EventTypePutDto eventTypePut) {
        EventType eventType;
        Optional<EventType> optionalEventType = findOptionalEventTypeByType(type);

        if (optionalEventType.isPresent()) {
            eventType = optionalEventType.get();
            modelMapper.map(eventTypePut, eventType);
        } else {
            eventType = modelMapper.map(eventTypePut, EventType.class);
            eventType.setType(type);
            eventTypeRepository.save(eventType);
        }

        if (StringUtils.isEmpty(eventType.getColor()))
            throw new ServiceException("'color' can't be empty");

        return eventType;
    }

    public void deleteEventType(String type) {
        try {
            eventTypeRepository.deleteById(type);
        } catch (EmptyResultDataAccessException ex) {
            log.error("", ex);
            throw notFindException(type);
        }

    }

    public List<EventType> getAllEventTypes() {
        return eventTypeRepository.findAll();
    }

    private Optional<EventType> findOptionalEventTypeByType(String type) {
        return eventTypeRepository.findById(type);
    }

    private EventType findEventTypeByType(String type) {
        return findOptionalEventTypeByType(type)
                .orElseThrow(() -> notFindException(type));
    }

    void exists(String type) {
        if (!eventTypeRepository.existsById(type))
            throw notFindException(type);
    }

    private ServiceException notFindException(String type) {
        return new ServiceException("Не найден тип события(type=" + type + ")");
    }


}
