package com.varb.schedule.buisness.logic.controllers.apiimpl;

import com.varb.schedule.buisness.logic.controllers.api.EventTypeApi;
import com.varb.schedule.buisness.logic.service.EventTypeService;
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
                modelMapper.mapList(eventTypeService.getAllEventTypes(), EventTypeResponseDto.class));
    }

    @Override
    public ResponseEntity<Void> eventTypeTypeDelete(String type) {
        eventTypeService.deleteEventType(type);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<EventTypeResponseDto> eventTypeTypePut(String type, @Valid EventTypePutDto eventTypePutDto) {
        return ResponseEntity.ok(
                modelMapper.map(eventTypeService.updateEventType(type, eventTypePutDto), EventTypeResponseDto.class));
    }
}
