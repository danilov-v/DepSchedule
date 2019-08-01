package com.varb.schedule.buisness.logic.service;

import com.varb.schedule.buisness.logic.repository.EventTypeRepository;
import com.varb.schedule.buisness.models.dto.EventTypePostDto;
import com.varb.schedule.buisness.models.dto.EventTypePutDto;
import com.varb.schedule.buisness.models.entity.EventType;
import com.varb.schedule.config.modelmapper.ModelMapperCustomize;
import com.varb.schedule.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public EventType update(Long typeId, EventTypePutDto eventTypePut) {
        EventType eventType = findById(typeId);
        modelMapper.map(eventTypePut, eventType);

//        if (StringUtils.isEmpty(eventType.getColor()))
//            throw new ServiceException("'color' can't be empty");

        return eventType;
    }

    public EventType add(EventTypePostDto eventTypePost) {
        EventType eventType = modelMapper.map(eventTypePost, EventType.class);
        return eventTypeRepository.save(eventType);
    }

    public void deleteEventType(Long typeId) {
        try {
            eventTypeRepository.deleteById(typeId);
        } catch (EmptyResultDataAccessException ex) {
            log.error("", ex);
            throw notFindException(typeId);
        }

    }

    public List<EventType> getAll() {
        return eventTypeRepository.findAll();
    }

    private EventType findById(Long typeId) {
        return eventTypeRepository.findById(typeId)
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
