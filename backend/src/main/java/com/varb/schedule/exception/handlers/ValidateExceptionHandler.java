package com.varb.schedule.exception.handlers;


import com.varb.schedule.exception.ServiceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

/**
 * Перехватывает ошибки валидации, которые возникают до выполнения бизнес-логики
 */
@ControllerAdvice
public class ValidateExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> "'" + fieldError.getField() + "' " + fieldError.getDefaultMessage())
                .collect(Collectors.joining("; "));
        ServiceException serviceException = new ServiceException(ex, errorMessage, HttpStatus.BAD_REQUEST);
        return handleExceptionInternal(serviceException, null, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ServiceException serviceException = new ServiceException(ex, ex.getMostSpecificCause().getMessage(), HttpStatus.BAD_REQUEST);
        return handleExceptionInternal(serviceException, null, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error("", ex);
        if (ex instanceof ServiceException)
            return ExceptionFormatter.toResponseEntity((ServiceException) ex);
        else
            return ExceptionFormatter.toResponseEntity(new ServiceException(ex, HttpStatus.INTERNAL_SERVER_ERROR));
    }
}


