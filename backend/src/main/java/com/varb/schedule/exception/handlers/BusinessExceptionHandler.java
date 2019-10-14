package com.varb.schedule.exception.handlers;

import com.varb.schedule.exception.WebApiException;
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
    @ExceptionHandler(WebApiException.class)
    ResponseEntity<Object> handleBadRequest(WebApiException webApiException) {
        log.error("", webApiException);
        return exceptionFormatter.toResponseEntity(webApiException);
    }

    /**
     * Нарушение ограничения целостности
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity<Object> handleException(DataIntegrityViolationException ex) {
        log.error("", ex);
        return exceptionFormatter.toResponseEntity(
                new WebApiException(ex,
                        StringEscapeUtils.unescapeJava(ex.getCause().getCause().getMessage()),
                        HttpStatus.BAD_REQUEST)
        );
    }
}
