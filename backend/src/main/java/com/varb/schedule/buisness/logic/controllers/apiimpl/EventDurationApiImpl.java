package com.varb.schedule.buisness.logic.controllers.apiimpl;

import com.varb.schedule.buisness.logic.controllers.api.EventDurationApi;
import com.varb.schedule.buisness.logic.service.EventDurationService;
import com.varb.schedule.buisness.models.dto.EventDurationPutDto;
import com.varb.schedule.buisness.models.dto.EventDurationResponseDto;
import com.varb.schedule.config.modelmapper.ModelMapperCustomize;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<List<EventDurationResponseDto>> eventDurationGet() {
        return ResponseEntity.ok(
                modelMapper.mapList(
                        eventDurationService.getAllEventDuration(),
                        EventDurationResponseDto.class));
    }

    @Override
    public ResponseEntity<EventDurationResponseDto> eventDurationPut(Long unitId, Long eventTypeId, @Valid EventDurationPutDto eventDurationPutDto) {
        return ResponseEntity.ok(
                modelMapper.map(
                        eventDurationService.updateEventDuration(unitId, eventTypeId, eventDurationPutDto),
                        EventDurationResponseDto.class));
    }

    @Override
    public ResponseEntity<Void> eventDurationDelete(Long unitId, Long eventType) {
        eventDurationService.deleteEventDuration(unitId, eventType);
        return ResponseEntity.ok().build();
    }

}
