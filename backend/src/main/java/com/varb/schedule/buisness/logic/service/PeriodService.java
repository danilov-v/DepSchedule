package com.varb.schedule.buisness.logic.service;

import com.varb.schedule.buisness.logic.repository.PeriodRepository;
import com.varb.schedule.buisness.models.dto.PeriodPostDto;
import com.varb.schedule.buisness.models.dto.PeriodPutDto;
import com.varb.schedule.buisness.models.entity.Period;
import com.varb.schedule.config.modelmapper.ModelMapperCustomize;
import com.varb.schedule.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class PeriodService extends AbstractService<Period, Long> {

    private final PeriodRepository periodRepository;
    private final ModelMapperCustomize modelMapper;
    private final ValidationService validationService;
    private static final String DATES_INTERSECTION = "DATES_INTERSECTION";
    private static final String DATES_INTERSECTION_MESSAGE = "Данный период пересекается с уже существующим. Проверьте даты его начала и окончания.";

    public Period add(PeriodPostDto periodPost) {
        Period period = modelMapper.map(periodPost, Period.class);
        checkBeforeSave(period);
        //if everything is ok - there's no intersections and dates are correct, process it further
        return periodRepository.save(period);
    }

    public Period update(Long periodId, PeriodPutDto periodPut) {
        Period period = findById(periodId);
        modelMapper.map(periodPut, period);
        checkBeforeSave(period);
        return period;
    }

    private void checkBeforeSave(Period period) {
        validationService.checkDates(period.getStartDate(), period.getEndDate());

        List<Period> intersections = periodRepository.
                findIntersections(period.getStartDate(), period.getEndDate());
        if (!intersections.isEmpty()) {
            String ids = intersections.stream().map(per -> per.getPeriodId().toString()).collect(Collectors.joining(","));
            throw new ServiceException(
                    "Period you want to add has intersections with other periods with ids: [" + ids + "])",
                    DATES_INTERSECTION_MESSAGE,
                    DATES_INTERSECTION);
        }
    }

    @Override
    protected String notFindMessage(Long aLong) {
        return "Не найден период с periodID = " + aLong;
    }
}
