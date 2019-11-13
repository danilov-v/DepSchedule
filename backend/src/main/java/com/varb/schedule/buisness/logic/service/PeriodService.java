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
    private static final String PARENT_HIERARCHY_VIOLATION_MESSAGE = "Даты периода выходят за пределы диапазона дат его родительского периода.";
    private static final String PARENT_HIERARCHY_VIOLATION = "PARENT_HIERARCHY_VIOLATION";
    private static final String CHILD_HIERARCHY_VIOLATION_MESSAGE = "Даты периода выходят за пределы диапазона дат его дочерних периодов.";
    private static final String CHILD_HIERARCHY_VIOLATION = "CHILD_HIERARCHY_VIOLATION";

    public Period add(PeriodReqDto periodPost) {
        Period period = modelMapper.map(periodPost, Period.class);
        checkConsistencyBeforeSave(period);
        return save(period);
    }

    public Period update(Long periodId, PeriodDto periodPut) {
        Period period = findById(periodId);
        modelMapper.map(periodPut, period);
        checkConsistencyBeforeSave(period);
        return period;
    }

    public List<Period> findAll(Long calendarId) {
        return periodRepository.findAllByCalendarId(calendarId);
    }

    private void checkConsistencyBeforeSave(Period period) {
        //validate updated period entity
        validationService.checkDates(period.getStartDate(), period.getEndDate());
        validateHierarchyDatesRange(period);
        checkIntersection(period);
    }

    private void checkIntersection(Period period) {
        List<Period> intersections = periodRepository.
                findIntersections(period.getCalendarId(), period.getParentId(), period.getStartDate(), period.getEndDate());

        //When we perform an update operation our period can have intersection with itself.
        //It is correct behaviour
        intersections.removeIf(p -> p.getPeriodId().equals(period.getPeriodId()));
        if (!intersections.isEmpty()) {
            //gather ids for exception message
            String ids = intersections.stream().map(per -> per.getPeriodId().toString()).collect(Collectors.joining(","));
            throw new WebApiException(
                    "Period you want to add has intersections with other periods with ids: [" + ids + "])",
                    DATES_INTERSECTION_MESSAGE,
                    DATES_INTERSECTION);
        }
    }

    /**
     * Checking: if Period out of hierarchy date range then throw {@link WebApiException}
     */
    private void validateHierarchyDatesRange(Period period) {

        //"periodId" may be null if entity has state "Transient".
        //It has just been instantiated using the new operator.
        //It has no persistent representation in the database and no identifier value has been assigned
        Long periodId = period.getPeriodId();
        if (periodId != null)
            validateHierarchyDatesRangeOfChildren(period, periodId);

        //"parentId" may be null if this is top level period
        Long parentId = period.getParentId();
        if (parentId != null)
            validateHierarchyDatesRangeOfParent(period, parentId);
    }

    /**
     * check out of bounds children
     */
    private void validateHierarchyDatesRangeOfChildren(Period period, Long periodId) {
        List<Period> outOfBoundsChilds = periodRepository.findOutOfBoundsChilds(periodId, period.getStartDate(), period.getEndDate());
        if (!outOfBoundsChilds.isEmpty()) {
            String ids = outOfBoundsChilds.stream().map(per -> per.getPeriodId().toString()).collect(Collectors.joining(","));
            throw new WebApiException(
                    "Dates of the child periods with ids [" + ids
                            + "] are out of the dates range of its parent with id = " + periodId,
                    CHILD_HIERARCHY_VIOLATION_MESSAGE,
                    CHILD_HIERARCHY_VIOLATION);
        }
    }

    /**
     * check out of bounds parent
     */
    private void validateHierarchyDatesRangeOfParent(Period period, Long parentId) {
        Period parentPeriod = findById(parentId);
        if (parentPeriod.getStartDate().isAfter(period.getStartDate()) ||
                parentPeriod.getEndDate().isBefore(period.getEndDate())) {
            throw new WebApiException(
                    "Dates of the period (child periodId = " + period.getPeriodId() +
                            ", startDate = " + period.getStartDate()
                            + ", endDate = " + period.getEndDate()
                            + ") are out of the range of its parent "
                            + "(parent periodId = " + parentId + ", startDate = " + parentPeriod.getStartDate()
                            + ", endDate = " + parentPeriod.getEndDate() + ").",
                    PARENT_HIERARCHY_VIOLATION_MESSAGE,
                    PARENT_HIERARCHY_VIOLATION);
        }
    }

    @Override
    protected String notFindMessage(Long periodId) {
        return "Не найден период с periodId = " + periodId;
    }
}
