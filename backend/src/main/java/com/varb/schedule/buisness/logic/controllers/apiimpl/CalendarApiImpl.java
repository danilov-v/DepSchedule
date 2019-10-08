package com.varb.schedule.buisness.logic.controllers.apiimpl;

import com.varb.schedule.buisness.logic.controllers.ApiController;
import com.varb.schedule.buisness.logic.controllers.api.CalendarApi;
import com.varb.schedule.buisness.logic.service.CalendarService;
import com.varb.schedule.buisness.models.business.PrivilegeEnum;
import com.varb.schedule.buisness.models.dto.CalendarBaseDto;
import com.varb.schedule.buisness.models.dto.CalendarBaseReqDto;
import com.varb.schedule.buisness.models.dto.InlineResponse200Dto;
import com.varb.schedule.buisness.models.entity.Calendar;
import com.varb.schedule.config.modelmapper.ModelMapperCustomize;
import com.varb.schedule.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@ApiController
@RequiredArgsConstructor
public class CalendarApiImpl implements CalendarApi {
    private final CalendarService calendarService;
    private final ModelMapperCustomize modelMapper;

    @Secured(PrivilegeEnum.Code.READ_WRITE)
    @Override
    public ResponseEntity<Void> calendarDelete(String name) {
        throw new ServiceException("Not supported yet", HttpStatus.NOT_IMPLEMENTED);
    }

    @Secured(PrivilegeEnum.Code.READ)
    @Override
    public ResponseEntity<InlineResponse200Dto> calendarNamesGet() {
        List<Calendar> calendarList = calendarService.findAll();

        List<String> calendarNames = calendarList.stream()
                .map(Calendar::getName)
                .collect(Collectors.toList());

        CalendarBaseReqDto active = modelMapper.map(calendarList.get(0), CalendarBaseReqDto.class); //TODO Заглушка!! Переделать

        return ResponseEntity.ok(new InlineResponse200Dto()
                .active(active)
                .calendarList(calendarNames));
    }

    @Secured(PrivilegeEnum.Code.READ)
    @Override
    public ResponseEntity<CalendarBaseReqDto> calendarGet(String name) {
        return ResponseEntity.ok(
                modelMapper.map(calendarService.findById(name), CalendarBaseReqDto.class));
    }

    @Override
    public ResponseEntity<CalendarBaseReqDto> calendarPostAndSetActive(@Valid CalendarBaseReqDto calendarBaseReqDto) {
        throw new ServiceException("Not supported yet", HttpStatus.NOT_IMPLEMENTED);
//        return ResponseEntity.ok(
//                modelMapper.map(calendarService.add(calendarBaseReqDto), CalendarBaseReqDto.class));
    }

    @Secured(PrivilegeEnum.Code.READ_WRITE)
    @Override
    public ResponseEntity<CalendarBaseReqDto> calendarPutAndSetActive(@NotNull @Valid String name, @Valid CalendarBaseDto calendarBaseDto) {
        return ResponseEntity.ok(
                modelMapper.map(calendarService.update(name, calendarBaseDto), CalendarBaseReqDto.class));
    }
}
