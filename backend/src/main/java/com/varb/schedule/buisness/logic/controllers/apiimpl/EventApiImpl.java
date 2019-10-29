package com.varb.schedule.buisness.logic.controllers.apiimpl;

import com.varb.schedule.buisness.logic.controllers.ApiController;
import com.varb.schedule.buisness.logic.controllers.api.EventApi;
import com.varb.schedule.buisness.logic.service.EventService;
import com.varb.schedule.buisness.models.business.PrivilegeEnum;
import com.varb.schedule.buisness.models.dto.EventPostDto;
import com.varb.schedule.buisness.models.dto.EventPutDto;
import com.varb.schedule.buisness.models.dto.EventRecentResponseDto;
import com.varb.schedule.buisness.models.dto.EventResponseDto;
import com.varb.schedule.config.modelmapper.ModelMapperCustomize;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ApiController
@RequiredArgsConstructor
public class EventApiImpl implements EventApi {
    private final EventService eventService;
    private final ModelMapperCustomize modelMapper;

    @Secured(PrivilegeEnum.Code.READ)
    @Override
    public ResponseEntity<List<EventResponseDto>> eventGet(@NotNull @Valid Long calendarId, @NotNull @Valid LocalDate dateFrom, @Valid Optional<LocalDate> dateTo) {
        return ResponseEntity.ok(
                modelMapper.mapToList(
                        eventService.getAllBetweenDates(calendarId, dateFrom, dateTo.orElse(null)),
                        EventResponseDto.class));
    }

    @Override
    public ResponseEntity<List<EventRecentResponseDto>> eventRecentList(@NotNull @Valid Long calendarId, @NotNull @Min(1) @Max(100) @Valid Integer count) {
        return ResponseEntity.ok(
                modelMapper.mapToList(
                        eventService.getRecent(calendarId, count),
                        EventRecentResponseDto.class));
    }



    @Secured(PrivilegeEnum.Code.READ_WRITE)
    @Override
    public ResponseEntity<EventResponseDto> eventPost(@Valid EventPostDto eventPostDto) {
        return ResponseEntity.ok(
                modelMapper.map(eventService.add(eventPostDto), EventResponseDto.class));
    }

    @Secured(PrivilegeEnum.Code.READ_WRITE)
    @Override
    public ResponseEntity<EventResponseDto> eventPut(Long eventId, @Valid EventPutDto eventPutDto) {
        return ResponseEntity.ok(
                modelMapper.map(eventService.update(eventId, eventPutDto), EventResponseDto.class));
    }

    @Secured(PrivilegeEnum.Code.READ_WRITE)
    @Override
    public ResponseEntity<Void> eventDelete(Long eventId) {
        eventService.delete(eventId);
        return ResponseEntity.ok().build();
    }
}
