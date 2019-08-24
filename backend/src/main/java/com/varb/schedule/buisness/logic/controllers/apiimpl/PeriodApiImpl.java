package com.varb.schedule.buisness.logic.controllers.apiimpl;

import com.varb.schedule.buisness.logic.controllers.api.PeriodApi;
import com.varb.schedule.buisness.logic.service.PeriodService;
import com.varb.schedule.buisness.models.dto.PeriodDto;
import com.varb.schedule.buisness.models.dto.PeriodPostDto;
import com.varb.schedule.buisness.models.dto.PeriodPutDto;
import com.varb.schedule.buisness.models.dto.PeriodResponseDto;
import com.varb.schedule.buisness.models.entity.Period;
import com.varb.schedule.config.modelmapper.ModelMapperCustomize;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PeriodApiImpl implements PeriodApi {

    private final PeriodService periodService;
    private final ModelMapperCustomize modelMapper;

    @Override
    public ResponseEntity<List<PeriodResponseDto>> periodGet() {
        return ResponseEntity.ok(
                modelMapper.mapList(periodService.getAll(), PeriodResponseDto.class));
    }

    @Override

    public ResponseEntity<PeriodResponseDto> periodPost(@Valid PeriodPostDto periodPost) {
        return ResponseEntity.ok(
                modelMapper.map(periodService.add(periodPost), PeriodResponseDto.class));
    }

    @Override
    public ResponseEntity<PeriodResponseDto> periodPut(Long periodId, @Valid PeriodPutDto periodPut) {
        Period period = periodService.update(periodId, periodPut);

        return ResponseEntity.ok(
                modelMapper.map(period, PeriodResponseDto.class));
    }

    @Override
    public ResponseEntity<Void> periodDelete(Long periodId) {
        periodService.delete(periodId);
        return ResponseEntity.ok().build();
    }
}
