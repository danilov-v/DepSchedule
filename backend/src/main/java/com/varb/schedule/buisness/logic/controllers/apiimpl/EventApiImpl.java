package com.varb.schedule.buisness.logic.controllers.apiimpl;

import com.varb.schedule.buisness.logic.controllers.api.EventApi;
import com.varb.schedule.buisness.logic.service.EventService;
import com.varb.schedule.buisness.models.dto.*;
import com.varb.schedule.config.modelmapper.ModelMapperCustomize;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Transactional
public class EventApiImpl implements EventApi {
    private final EventService eventService;
    private final ModelMapperCustomize modelMapper;

    @Override
    public ResponseEntity<Void> eventDelete(Long eventId) {
        eventService.delete(eventId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<EventResponseDto> eventPut(Long eventId, @Valid EventPutDto eventPutDto) {
        return ResponseEntity.ok(
                modelMapper.map(eventService.update(eventId, eventPutDto), EventResponseDto.class));
    }

    @Override
    public ResponseEntity<List<EventResponseDto>> eventGet(@NotNull @Valid LocalDate dateFrom, @Valid Optional<LocalDate> dateTo) {
        return ResponseEntity.ok(
                modelMapper.mapList(
                        eventService.getAllBetweenDates(dateFrom, dateTo.orElse(null)),
                        EventResponseDto.class));
    }


    @Override
    public ResponseEntity<EventResponseDto> eventPost(@Valid EventPostDto eventPostDto) {
        return ResponseEntity.ok(
                modelMapper.map(eventService.add(eventPostDto), EventResponseDto.class));
    }
}
