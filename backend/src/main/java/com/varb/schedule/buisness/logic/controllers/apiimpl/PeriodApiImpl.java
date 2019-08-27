package com.varb.schedule.buisness.logic.controllers.apiimpl;

import com.varb.schedule.buisness.logic.controllers.ApiController;
import com.varb.schedule.buisness.logic.controllers.api.PeriodApi;
import com.varb.schedule.buisness.logic.service.PeriodService;
import com.varb.schedule.buisness.models.business.PrivilegeEnum;
import com.varb.schedule.buisness.models.dto.PeriodPostDto;
import com.varb.schedule.buisness.models.dto.PeriodPutDto;
import com.varb.schedule.buisness.models.dto.PeriodResponseDto;
import com.varb.schedule.buisness.models.entity.Period;
import com.varb.schedule.config.modelmapper.ModelMapperCustomize;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;

import javax.validation.Valid;
import java.util.List;

@ApiController
@RequiredArgsConstructor
public class PeriodApiImpl implements PeriodApi {

    private final PeriodService periodService;
    private final ModelMapperCustomize modelMapper;

    @Secured(PrivilegeEnum.Code.READ)
    @Override
    public ResponseEntity<List<PeriodResponseDto>> periodGet() {
        return ResponseEntity.ok(
                modelMapper.mapList(periodService.findAll(), PeriodResponseDto.class));
    }

    @Secured(PrivilegeEnum.Code.READ_WRITE)
    @Override
    public ResponseEntity<PeriodResponseDto> periodPost(@Valid PeriodPostDto periodPost) {
        return ResponseEntity.ok(
                modelMapper.map(periodService.add(periodPost), PeriodResponseDto.class));
    }

    @Secured(PrivilegeEnum.Code.READ_WRITE)
    @Override
    public ResponseEntity<PeriodResponseDto> periodPut(Long periodId, @Valid PeriodPutDto periodPut) {
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
}
