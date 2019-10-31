package com.varb.schedule.buisness.logic.controllers.apiimpl;

import com.varb.schedule.buisness.logic.controllers.ApiController;
import com.varb.schedule.buisness.logic.controllers.api.EventTypeApi;
import com.varb.schedule.buisness.logic.service.EventTypeService;
import com.varb.schedule.buisness.models.business.PrivilegeEnum;
import com.varb.schedule.buisness.models.dto.EventTypeDto;
import com.varb.schedule.buisness.models.dto.EventTypeReqDto;
import com.varb.schedule.buisness.models.dto.EventTypeResponseDto;
import com.varb.schedule.config.modelmapper.ModelMapperCustomize;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;

import javax.validation.Valid;
import java.util.List;

@ApiController
@RequiredArgsConstructor
public class EventTypeApiImpl implements EventTypeApi {
    private final EventTypeService eventTypeService;
    private final ModelMapperCustomize modelMapper;

    @Secured(PrivilegeEnum.Code.READ)
    @Override
    public ResponseEntity<List<EventTypeResponseDto>> eventTypeGet() {
        return ResponseEntity.ok(
                modelMapper.mapToList(eventTypeService.findAll(), EventTypeResponseDto.class));
    }

    @Secured(PrivilegeEnum.Code.READ_WRITE)
    @Override
    public ResponseEntity<EventTypeResponseDto> eventTypePost(@Valid EventTypeReqDto eventTypePostDto) {
        return ResponseEntity.ok(
                modelMapper.map(eventTypeService.add(eventTypePostDto), EventTypeResponseDto.class));
    }

    @Secured(PrivilegeEnum.Code.READ_WRITE)
    @Override
    public ResponseEntity<EventTypeResponseDto> eventTypePatch(Long typeId, @Valid EventTypeDto eventTypePutDto) {
        return ResponseEntity.ok(
                modelMapper.map(eventTypeService.update(typeId, eventTypePutDto), EventTypeResponseDto.class));
    }

    @Secured(PrivilegeEnum.Code.READ_WRITE)
    @Override
    public ResponseEntity<Void> eventTypeDelete(Long typeId) {
        eventTypeService.delete(typeId);
        return ResponseEntity.ok().build();
    }


}
