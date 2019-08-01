package com.varb.schedule.buisness.logic.controllers.apiimpl;

import com.varb.schedule.buisness.logic.controllers.api.EventTypeApi;
import com.varb.schedule.buisness.logic.service.EventTypeService;
import com.varb.schedule.buisness.models.dto.EventTypePostDto;
import com.varb.schedule.buisness.models.dto.EventTypePutDto;
import com.varb.schedule.buisness.models.dto.EventTypeResponseDto;
import com.varb.schedule.config.modelmapper.ModelMapperCustomize;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class EventTypeApiImpl implements EventTypeApi {
    private final EventTypeService eventTypeService;
    private final ModelMapperCustomize modelMapper;

    @Override
    public ResponseEntity<List<EventTypeResponseDto>> eventTypeGet() {
        return ResponseEntity.ok(
                modelMapper.mapList(eventTypeService.getAll(), EventTypeResponseDto.class));
    }

    @Override
    public ResponseEntity<Void> eventTypeDelete(Long typeId) {
        eventTypeService.deleteEventType(typeId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<EventTypeResponseDto> eventTypePut(Long typeId, @Valid EventTypePutDto eventTypePutDto) {
        return ResponseEntity.ok(
                modelMapper.map(eventTypeService.update(typeId, eventTypePutDto), EventTypeResponseDto.class));
    }

    @Override
    public ResponseEntity<EventTypeResponseDto> eventTypePost(@Valid EventTypePostDto eventTypePostDto) {
        return ResponseEntity.ok(
                modelMapper.map(eventTypeService.add(eventTypePostDto), EventTypeResponseDto.class));
    }
}
