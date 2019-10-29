package com.varb.schedule.buisness.logic.service;

import com.varb.schedule.buisness.logic.repository.PeriodRepository;
import com.varb.schedule.buisness.models.dto.PeriodPostDto;
import com.varb.schedule.buisness.models.dto.PeriodPutDto;
import com.varb.schedule.buisness.models.entity.Period;
import com.varb.schedule.config.modelmapper.ModelMapperCustomize;
import com.varb.schedule.exception.WebApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
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
    public static final String DATES_INTERSECTION = "DATES_INTERSECTION";
    private static final String DATES_INTERSECTION_MESSAGE = "Данный период пересекается с уже существующим. Проверьте даты его начала и окончания.";

    public Period add(PeriodPostDto periodPost) {
        Period period = modelMapper.map(periodPost, Period.class);
        checkBeforeSave(period);
        //if everything is ok - there's no intersections and dates are correct, process it further
        return save(period);
    }

    public Period update(Long periodId, PeriodPutDto periodPut) {
        Period period = findById(periodId);
        modelMapper.map(periodPut, period);
        checkBeforeUpdate(period);
        return period;
    }

    public List<Period> findAll(Long calendarId) {
        return periodRepository.findAllByCalendarId(calendarId);
    }

    private void checkBeforeUpdate(Period period) {
        //validate updated period entity
        validationService.checkDates(period.getStartDate(), period.getEndDate());

        List<Period> intersections = periodRepository.
                findIntersections(period.getCalendarId(), period.getStartDate(), period.getEndDate());

        //When we perform an update operation our period can have intersection with itself.
        //It is correct behaviour
        intersections.removeIf(p -> p.getPeriodId().equals(period.getPeriodId()));
        if (!intersections.isEmpty()) {
            throw new WebApiException(
                    intersectionsExceptionDevMessage(intersections),
                    DATES_INTERSECTION_MESSAGE,
                    DATES_INTERSECTION);
        }
    }

    private void checkBeforeSave(Period period) {
        validationService.checkDates(period.getStartDate(), period.getEndDate());

        List<Period> intersections = periodRepository.
                findIntersections(period.getCalendarId(), period.getStartDate(), period.getEndDate());
        if (!intersections.isEmpty()) {
            throw new WebApiException(
                    intersectionsExceptionDevMessage(intersections),
                    DATES_INTERSECTION_MESSAGE,
                    DATES_INTERSECTION);
        }
    }

    private String intersectionsExceptionDevMessage(List<Period> intersections) {
        //gather ids for exception message
        String ids = intersections.stream().map(per -> per.getPeriodId().toString()).collect(Collectors.joining(","));
        return "Period you want to add has intersections with other periods with ids: [" + ids + "])";
    }

    @Override
    protected String notFindMessage(Long aLong) {
        return "Не найден период с periodID = " + aLong;
    }
}
