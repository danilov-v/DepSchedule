package com.varb.schedule.exception.handlers;

import com.varb.schedule.exception.ServiceException;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BuisnessExceptionHandler {
    @ExceptionHandler(ServiceException.class)
    ResponseEntity<Object> handleBadRequest(ServiceException serviceException) {
        return ExceptionFormatter.toResponseEntity(serviceException);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity<Object> handleException(DataIntegrityViolationException ex) {
        return ExceptionFormatter.toResponseEntity(new ServiceException(ex, StringEscapeUtils.unescapeJava(ex.getCause().getCause().getMessage()), HttpStatus.BAD_REQUEST));
    }
}
