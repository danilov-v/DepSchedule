package com.varb.schedule.exception.handlers;

import com.varb.schedule.buisness.models.dto.ErrorMessageDto;
import com.varb.schedule.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

@Slf4j
class ExceptionFormatter {
    //A single place to customize the response body
    static ErrorMessageDto toErrorMessageDto(ServiceException serviceException) {
        log.error("", serviceException);
        return new ErrorMessageDto()
                .code(serviceException.getCode() != null ? serviceException.getCode() :
                        String.valueOf(serviceException.getHttpStatus().value()))
                .devMessage(serviceException.getMessage())
                .userMessage(serviceException.getUserMessage());

    }

    static ResponseEntity<Object> toResponseEntity(ServiceException serviceException) {
        return ResponseEntity.status(serviceException.getHttpStatus()).body(
                ExceptionFormatter.toErrorMessageDto(serviceException));
    }
}
