package com.varb.schedule.exception;

import com.varb.schedule.buisness.models.dto.ErrorMessageDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

/**
 * Перехватывает ошибки, порождённые в бизнес логике
 */
@RestControllerAdvice
public class GlobalExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    ErrorMessageDto
    handleBadRequest(HttpServletResponse res, ServiceException serviceException) {
        logger.error("", serviceException);
        res.setStatus(serviceException.getHttpStatus().value());
        return new ErrorMessageDto()
                .code(serviceException.getCode() != null ? serviceException.getCode() :
                        String.valueOf(serviceException.getHttpStatus().value()))
                .devMessage(serviceException.getMessage())
                .userMessage(serviceException.getUserMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    ErrorMessageDto
    handleException(HttpServletResponse res, Exception ex) {
        logger.error("", ex);
        res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ErrorMessageDto()
                .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .devMessage(ex.getMessage());
    }

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
            return handleServiceException((ServiceException) ex);
        else
            return handleException(ex);
    }

    /**
     * Customize response of {@link ServiceException}
     */
    private ResponseEntity<Object> handleServiceException(ServiceException serviceException) {
        return ResponseEntity.status(serviceException.getHttpStatus()).body(
                new ErrorMessageDto()
                        .code(serviceException.getCode() != null ? serviceException.getCode() :
                                String.valueOf(serviceException.getHttpStatus().value()))
                        .devMessage(serviceException.getMessage()));
    }

    /**
     * Customize the response of all Exception but {@link ServiceException}.
     */
    private ResponseEntity<Object> handleException(Exception ex) {
        return ResponseEntity.badRequest().body(
                new ErrorMessageDto()
                        .devMessage(ex.getMessage())
                        .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())));
    }
}


