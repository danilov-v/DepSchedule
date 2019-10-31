package com.varb.schedule.buisness.logic.controllers.apiimpl;

import com.varb.schedule.buisness.logic.controllers.ApiController;
import com.varb.schedule.buisness.logic.controllers.api.EventDurationApi;
import com.varb.schedule.buisness.logic.service.EventDurationService;
import com.varb.schedule.buisness.models.business.PrivilegeEnum;
import com.varb.schedule.buisness.models.dto.EventDurationReqDto;
import com.varb.schedule.buisness.models.dto.EventDurationResponseDto;
import com.varb.schedule.config.modelmapper.ModelMapperCustomize;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@ApiController
@RequiredArgsConstructor
public class EventDurationApiImpl implements EventDurationApi {
    private final EventDurationService eventDurationService;
    private final ModelMapperCustomize modelMapper;


    @Secured(PrivilegeEnum.Code.READ)
    @Override
    public ResponseEntity<List<EventDurationResponseDto>> eventDurationGet(@Valid Optional<Long> unitId) {
        return ResponseEntity.ok(
                modelMapper.mapToList(
                        eventDurationService.getAll(unitId.orElse(null)),
                        EventDurationResponseDto.class));
    }

    @Secured(PrivilegeEnum.Code.READ_WRITE)
    @Override
    public ResponseEntity<EventDurationResponseDto> eventDurationPatch(Long unitId, Long eventTypeId, @Valid EventDurationReqDto eventDurationPutDto) {
        return ResponseEntity.ok(
                modelMapper.map(
                        eventDurationService.merge(unitId, eventTypeId, eventDurationPutDto),
                        EventDurationResponseDto.class));
    }

    @Secured(PrivilegeEnum.Code.READ_WRITE)
    @Override
    public ResponseEntity<Void> eventDurationDelete(Long unitId, Long eventType) {
        eventDurationService.delete(unitId, eventType);
        return ResponseEntity.ok().build();
    }

}
