package com.varb.schedule.buisness.logic.service;

import com.varb.schedule.buisness.logic.repository.PeriodRepository;
import com.varb.schedule.buisness.models.dto.PeriodDto;
import com.varb.schedule.buisness.models.dto.PeriodReqDto;
import com.varb.schedule.buisness.models.entity.Period;
import com.varb.schedule.config.modelmapper.ModelMapperCustomize;
import com.varb.schedule.exception.WebApiException;
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
    public static final String DATES_INTERSECTION = "DATES_INTERSECTION";
    private static final String DATES_INTERSECTION_MESSAGE = "Данный период пересекается с уже существующим. Проверьте даты его начала и окончания.";
    private static final String HIERARCHY_DATES_RANGE_VIOLATION_MESSAGE = "Даты периода выходят за пределы диапазона дат его родительского периода.";
    private static final String HIERARCHY_DATES_RANGE_VIOLATION = "HIERARCHY_DATES_RANGE_VIOLATION";

    public Period add(PeriodReqDto periodPost) {
        Period period = modelMapper.map(periodPost, Period.class);
        checkBeforeSave(period);
        //if everything is ok - there's no intersections and dates are correct, process it further
        return save(period);
    }

    public Period update(Long periodId, PeriodDto periodPut) {
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
        validateHierarchyDatesRange(period);

        List<Period> intersections = periodRepository.
                findIntersections(period.getCalendarId(), period.getParentId(), period.getStartDate(), period.getEndDate());

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
        validateHierarchyDatesRange(period);

        List<Period> intersections = periodRepository.
                findIntersections(period.getCalendarId(), period.getParentId(), period.getStartDate(), period.getEndDate());
        if (!intersections.isEmpty()) {
            throw new WebApiException(
                    intersectionsExceptionDevMessage(intersections),
                    DATES_INTERSECTION_MESSAGE,
                    DATES_INTERSECTION);
        }
    }

    private void validateHierarchyDatesRange(Period period) {
        Long parentId = period.getParentId();
        if (parentId == null) {
            return;
        }

        Period parentPeriod = findById(parentId);
        if (parentPeriod.getStartDate().isAfter(period.getStartDate()) ||
        parentPeriod.getEndDate().isBefore(period.getEndDate())) {
            throw new WebApiException(
                    "Dates of the period (child periodId = " +period.getPeriodId() +
                            ", startDate = " +period.getStartDate()
                            +", endDate = " +period.getEndDate()
                            +") are out of the range of its parent "
                            +"(parent periodId = " +parentId +", startDate = " +parentPeriod.getStartDate()
                            +", endDate = " +parentPeriod.getEndDate() +").",
                    HIERARCHY_DATES_RANGE_VIOLATION_MESSAGE,
                    HIERARCHY_DATES_RANGE_VIOLATION);
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
