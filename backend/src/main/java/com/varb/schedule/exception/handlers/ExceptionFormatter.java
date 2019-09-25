package com.varb.schedule.exception.handlers;

import com.varb.schedule.buisness.models.dto.ErrorMessageDto;
import com.varb.schedule.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ExceptionFormatter {
    private ErrorMessageDto toErrorMessageDto(ServiceException serviceException) {
        return new ErrorMessageDto()
                .code(serviceException.getCode() != null ? serviceException.getCode() :
                        String.valueOf(serviceException.getHttpStatus().value()))
                .devMessage(serviceException.getMessage())
                .userMessage(serviceException.getUserMessage());
    }

    //A single place to customize the response body
    public ResponseEntity<Object> toResponseEntity(ServiceException serviceException) {
        return ResponseEntity.status(serviceException.getHttpStatus()).body(
                toErrorMessageDto(serviceException));
    }
}
