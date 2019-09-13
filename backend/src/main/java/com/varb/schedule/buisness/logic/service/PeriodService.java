package com.varb.schedule.buisness.logic.service;

import com.varb.schedule.buisness.logic.repository.PeriodRepository;
import com.varb.schedule.buisness.models.dto.PeriodPostDto;
import com.varb.schedule.buisness.models.dto.PeriodPutDto;
import com.varb.schedule.buisness.models.entity.Period;
import com.varb.schedule.config.modelmapper.ModelMapperCustomize;
import com.varb.schedule.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class PeriodService extends AbstractService<Period, Long> {

    private final PeriodRepository periodRepository;
    private final ModelMapperCustomize modelMapper;
    private final String DATES_INTERSECTION = "DATES_INTERSECTION";
    private final String DATES_INTERSECTION_MESSAGE = "Данный период пересекается с уже существующим. Проверьте даты его начала и окончания.";

    private final String WRONG_DATES = "WRONG_DATES";
    private final String WRONG_DATES_MESSAGE = "Дата начала периода должна предшествовать его окончанию.";

    public Period add(PeriodPostDto periodPost) {
        if (periodPost.getStartDate().isBefore(periodPost.getEndDate())) {
            List<Period> intersections = periodRepository.
                    findIntersections(periodPost.getStartDate(), periodPost.getEndDate());
            if(!intersections.isEmpty()) {
                String ids = intersections.stream().map(period -> period.getPeriodId().toString()).collect(Collectors.joining(","));
                throw new ServiceException(
                        "Period you want to add has intersections with other periods with ids: " +ids,
                        DATES_INTERSECTION_MESSAGE,
                        DATES_INTERSECTION);
            }

        } else {
            throw new ServiceException(
                    "startDate " +periodPost.getStartDate() +" >= " +"endDate " +periodPost.getEndDate(),
                    WRONG_DATES_MESSAGE,
                    WRONG_DATES);
        }
        //if everything is ok - there's no intersections and dates are correct, process it further
        return periodRepository.save(modelMapper.map(periodPost, Period.class));
    }

    public Period update(Long periodId, PeriodPutDto periodPut) {
        Period period = findById(periodId);
        modelMapper.map(periodPut, period);
        return period;
    }

    @Override
    protected String notFindMessage(Long aLong) {
        return "Не найден период с periodID = " +aLong;
    }
}
