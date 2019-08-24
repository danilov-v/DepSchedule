package com.varb.schedule.buisness.logic.service;

import com.varb.schedule.buisness.logic.repository.PeriodRepository;
import com.varb.schedule.buisness.models.dto.PeriodPostDto;
import com.varb.schedule.buisness.models.dto.PeriodPutDto;
import com.varb.schedule.buisness.models.entity.Period;
import com.varb.schedule.config.modelmapper.ModelMapperCustomize;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class PeriodService extends AbstractService<Period, Long> {

    private final PeriodRepository periodRepository;
    private final ModelMapperCustomize modelMapper;

    public Period add(PeriodPostDto periodPost) {
        Period period = modelMapper.map(periodPost, Period.class);
        return periodRepository.save(period);
    }

    public Period update(Long periodId, PeriodPutDto periodPut) {
        Period period = findById(periodId);
        modelMapper.map(periodPut, period);
        return period;
    }

    @Override
    String notFindMessage(Long aLong) {
        return "Не найден период с periodID = " +aLong;
    }
}
