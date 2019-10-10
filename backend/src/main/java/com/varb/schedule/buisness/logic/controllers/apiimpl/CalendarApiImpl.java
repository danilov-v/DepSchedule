package com.varb.schedule.buisness.logic.controllers.apiimpl;

import com.varb.schedule.buisness.logic.controllers.ApiController;
import com.varb.schedule.buisness.logic.controllers.api.CalendarApi;
import com.varb.schedule.buisness.logic.service.CalendarService;
import com.varb.schedule.buisness.models.business.PrivilegeEnum;
import com.varb.schedule.buisness.models.dto.CalendarBaseDto;
import com.varb.schedule.buisness.models.dto.CalendarBaseReqDto;
import com.varb.schedule.buisness.models.dto.CalendarResponseDto;
import com.varb.schedule.config.modelmapper.ModelMapperCustomize;
import com.varb.schedule.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;

import javax.validation.Valid;
import java.util.List;

@ApiController
@RequiredArgsConstructor
public class CalendarApiImpl implements CalendarApi {
    private final CalendarService calendarService;
    private final ModelMapperCustomize modelMapper;

    @Secured(PrivilegeEnum.Code.READ_WRITE)
    @Override
    public ResponseEntity<Void> calendarDelete(Long calendarId) {
        throw new ServiceException("Not supported yet", HttpStatus.NOT_IMPLEMENTED);
//        calendarService.delete(calendarId);
//        return ResponseEntity.ok().build();
    }

    @Secured(PrivilegeEnum.Code.READ)
    @Override
    public ResponseEntity<List<CalendarResponseDto>> calendarGet() {
        return ResponseEntity.ok(
                modelMapper.mapList(calendarService.findAll(), CalendarResponseDto.class));
    }

    @Secured(PrivilegeEnum.Code.READ)
    @Override
    public ResponseEntity<CalendarResponseDto> calendarGetById(Long calendarId) {
        return ResponseEntity.ok(
                modelMapper.map(calendarService.findById(calendarId), CalendarResponseDto.class));
    }

    @Secured(PrivilegeEnum.Code.READ_WRITE)
    @Override
    public ResponseEntity<CalendarResponseDto> calendarPost(@Valid CalendarBaseReqDto calendarBaseReqDto) {
        throw new ServiceException("Not supported yet", HttpStatus.NOT_IMPLEMENTED);
//        return ResponseEntity.ok(
//                modelMapper.map(calendarService.add(calendarBaseReqDto), CalendarBaseReqDto.class));
    }

    @Secured(PrivilegeEnum.Code.READ_WRITE)
    @Override
    public ResponseEntity<CalendarResponseDto> calendarPut(Long calendarId, @Valid CalendarBaseDto calendarBaseDto) {
        return ResponseEntity.ok(
                modelMapper.map(calendarService.update(calendarId, calendarBaseDto), CalendarResponseDto.class));
    }

}
