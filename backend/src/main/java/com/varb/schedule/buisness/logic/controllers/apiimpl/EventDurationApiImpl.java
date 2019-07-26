package com.varb.schedule.buisness.logic.controllers.apiimpl;

import com.varb.schedule.buisness.logic.controllers.api.EventDurationApi;
import com.varb.schedule.buisness.logic.service.EventDurationService;
import com.varb.schedule.buisness.models.dto.*;
import com.varb.schedule.config.modelmapper.ModelMapperCustomize;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class EventDurationApiImpl implements EventDurationApi {
    private final EventDurationService eventDurationService;
    private final ModelMapperCustomize modelMapper;

    @Override
    public ResponseEntity<List<EventDurationDto>> eventDurationGet() {
        return ResponseEntity.ok(
                modelMapper.mapList(eventDurationService.getAllEventDuration(), EventDurationDto.class));
    }

    @Override
    public ResponseEntity<EventDurationDto> eventDurationPost(@Valid EventDurationDto eventDurationDto) {
//        return ResponseEntity.ok(
//                modelMapper.map(eventDurationService.addEventDuration(eventDurationDto), EventDurationDto.class));
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @Override
    public ResponseEntity<EventDurationDto> eventDurationPut(@Valid EventDurationDto eventDurationDto) {
        return ResponseEntity.ok(
                modelMapper.map(eventDurationService.updateEventDuration(eventDurationDto), EventDurationDto.class));
    }

    @Override
    public ResponseEntity<Void> eventDurationUnitIdEventTypeDelete(Long unitId, String eventType) {
        eventDurationService.deleteEventDuration(unitId,eventType);
        return ResponseEntity.ok().build();
    }
}
