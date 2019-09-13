package com.varb.schedule.exception.handlers;


import com.varb.schedule.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

/**
 * Перехватывает ошибки валидации, которые возникают до выполнения бизнес-логики
 */
@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ValidateExceptionHandler extends ResponseEntityExceptionHandler {
    private final ExceptionFormatter exceptionFormatter;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> "'" + fieldError.getField() + "' " + fieldError.getDefaultMessage())
                .collect(Collectors.joining("; "));
        ServiceException serviceException = new ServiceException(ex, errorMessage, HttpStatus.BAD_REQUEST);
        return handleServiceExceptionInternal(serviceException);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ServiceException serviceException = new ServiceException(ex, ex.getMostSpecificCause().getMessage(), HttpStatus.BAD_REQUEST);
        return handleServiceExceptionInternal(serviceException);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleServiceExceptionInternal(new ServiceException(ex, HttpStatus.BAD_REQUEST));
    }

    protected ResponseEntity<Object> handleServiceExceptionInternal(ServiceException ex) {
        log.error("", ex);
        return exceptionFormatter.toResponseEntity(ex);
    }
}


