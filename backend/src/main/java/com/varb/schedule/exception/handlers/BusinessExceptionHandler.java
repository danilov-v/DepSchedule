package com.varb.schedule.exception.handlers;

import com.varb.schedule.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Перехватывает ошибки бизнес-логики
 */
@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class BusinessExceptionHandler {
    private final ExceptionFormatter exceptionFormatter;

    /**
     * Исключения сервисного слоя
     */
    @ExceptionHandler(ServiceException.class)
    ResponseEntity<Object> handleBadRequest(ServiceException serviceException) {
        log.error("", serviceException);
        return exceptionFormatter.toResponseEntity(serviceException);
    }

    /**
     * Нарушение ограничения целостности
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity<Object> handleException(DataIntegrityViolationException ex) {
        log.error("", ex);
        return exceptionFormatter.toResponseEntity(
                new ServiceException(ex,
                        StringEscapeUtils.unescapeJava(ex.getCause().getCause().getMessage()),
                        HttpStatus.BAD_REQUEST)
        );
    }
}
