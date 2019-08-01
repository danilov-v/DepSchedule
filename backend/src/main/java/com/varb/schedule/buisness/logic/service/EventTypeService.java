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
    private final static Function<String, ServiceException> NOT_FIND = (type) -> new ServiceException("Не найден eventTypeId(type=" + type + ")");

    public EventType mergeEventType(Long typeId, EventTypePutDto eventTypePut) {
        EventType eventType;
        Optional<EventType> optionalEventType = findOptionalEventTypeByType(typeId);

        if (optionalEventType.isPresent()) {
            eventType = optionalEventType.get();
            modelMapper.map(eventTypePut, eventType);
        } else {
            eventType = modelMapper.map(eventTypePut, EventType.class);
            eventType.setTypeId(typeId);
            eventTypeRepository.save(eventType);
        }

        if (StringUtils.isEmpty(eventType.getColor()))
            throw new ServiceException("'color' can't be empty");

        return eventType;
    }

    public void deleteEventType(Long typeId) {
        try {
            eventTypeRepository.deleteById(typeId);
        } catch (EmptyResultDataAccessException ex) {
            log.error("", ex);
            throw notFindException(typeId);
        }

    }

    public List<EventType> getAllEventTypes() {
        return eventTypeRepository.findAll();
    }

    private Optional<EventType> findOptionalEventTypeByType(Long typeId) {
        return eventTypeRepository.findById(typeId);
    }

    private EventType findEventTypeByType(Long typeId) {
        return findOptionalEventTypeByType(typeId)
                .orElseThrow(() -> notFindException(typeId));
    }

    void exists(Long typeId) {
        if (!eventTypeRepository.existsById(typeId))
            throw notFindException(typeId);
    }

    private ServiceException notFindException(Long typeId) {
        return new ServiceException("Не найден тип события(typeId=" + typeId + ")");
    }


}
