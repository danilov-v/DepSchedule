package com.varb.schedule.buisness.logic.controllers.apiimpl;

import com.varb.schedule.buisness.logic.controllers.ApiController;
import com.varb.schedule.buisness.logic.controllers.api.PeriodApi;
import com.varb.schedule.buisness.logic.service.PeriodService;
import com.varb.schedule.buisness.models.business.PrivilegeEnum;
import com.varb.schedule.buisness.models.dto.PeriodDto;
import com.varb.schedule.buisness.models.dto.PeriodReqDto;
import com.varb.schedule.buisness.models.dto.PeriodResponseDto;
import com.varb.schedule.buisness.models.dto.PeriodResponseTreeDto;
import com.varb.schedule.buisness.models.entity.Period;
import com.varb.schedule.buisness.models.mappers.PeriodMapper;
import com.varb.schedule.config.modelmapper.ModelMapperCustomize;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@ApiController
@RequiredArgsConstructor
public class PeriodApiImpl implements PeriodApi {

    private final PeriodService periodService;
    private final ModelMapperCustomize modelMapper;
    private final PeriodMapper periodMapper;

    @Secured(PrivilegeEnum.Code.READ)
    @Override
    public ResponseEntity<List<PeriodResponseDto>> periodGet(Optional<Long> calendarId) {
        List<Period> resultList;
        if (calendarId.isPresent()) {
            resultList = periodService.findAll(calendarId.get());
        } else {
            resultList = periodService.findAll();
        }
        return ResponseEntity.ok(
                modelMapper.mapToList(resultList, PeriodResponseDto.class));
    }

    @Secured(PrivilegeEnum.Code.READ_WRITE)
    @Override
    public ResponseEntity<PeriodResponseDto> periodPost(@Valid PeriodReqDto periodPost) {
        return ResponseEntity.ok(
                modelMapper.map(periodService.add(periodPost), PeriodResponseDto.class));
    }

    @Secured(PrivilegeEnum.Code.READ_WRITE)
    @Override
    public ResponseEntity<PeriodResponseDto> periodPatch(Long periodId, @Valid PeriodDto periodPut) {
        Period period = periodService.update(periodId, periodPut);

        return ResponseEntity.ok(
                modelMapper.map(period, PeriodResponseDto.class));
    }

    @Secured(PrivilegeEnum.Code.READ_WRITE)
    @Override
    public ResponseEntity<Void> periodDelete(Long periodId) {
        periodService.delete(periodId);
        return ResponseEntity.ok().build();
    }

    @Secured(PrivilegeEnum.Code.READ)
    @Override
    public ResponseEntity<List<PeriodResponseTreeDto>> periodGetTree(Optional<Long> calendarId) {
        List<Period> resultList;
        if (calendarId.isPresent()) {
            resultList = periodService.findAll(calendarId.get());
        } else {
            resultList = periodService.findAll();
        }
        return ResponseEntity.ok(periodMapper.convertToTree(resultList));
    }
}
