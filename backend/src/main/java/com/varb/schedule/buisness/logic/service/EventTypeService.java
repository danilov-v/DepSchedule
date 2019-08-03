package com.varb.schedule.buisness.logic.service;

import com.varb.schedule.buisness.logic.repository.EventTypeRepository;
import com.varb.schedule.buisness.models.dto.EventTypePostDto;
import com.varb.schedule.buisness.models.dto.EventTypePutDto;
import com.varb.schedule.buisness.models.entity.EventType;
import com.varb.schedule.config.modelmapper.ModelMapperCustomize;
import com.varb.schedule.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class EventTypeService extends AbstractService<EventType, Long> {
    private final EventTypeRepository eventTypeRepository;
    private final ModelMapperCustomize modelMapper;

    public EventTypeService(EventTypeRepository repository, ModelMapperCustomize modelMapper) {
        super(repository, modelMapper);
        this.eventTypeRepository = repository;
        this.modelMapper = modelMapper;
    }

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

    @Override
    protected ServiceException notFindException(Long typeId) {
        return new ServiceException("Не найден тип события(typeId=" + typeId + ")");
    }


}
