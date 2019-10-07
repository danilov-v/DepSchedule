package com.varb.schedule.buisness.logic.service;

import com.varb.schedule.exception.ServiceException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ValidationService {
    public static final String WRONG_DATES = "WRONG_DATES";
    private static final String WRONG_DATES_MESSAGE = "Дата начала должна предшествовать дате окончания.";

    public void checkDates(LocalDate dateFrom, LocalDate dateTo) {
        if (dateTo.compareTo(dateFrom) < 0) //if dateTo < dateFrom
            throw new ServiceException(
                    "startDate " + dateFrom + " >= " + "endDate " + dateTo,
                    WRONG_DATES_MESSAGE,
                    WRONG_DATES);
    }
}
