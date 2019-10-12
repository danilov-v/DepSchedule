package com.varb.schedule.exception.handlers;

import com.varb.schedule.buisness.models.dto.ErrorMessageDto;
import com.varb.schedule.exception.WebApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ExceptionFormatter {
    private ErrorMessageDto toErrorMessageDto(WebApiException webApiException) {
        return new ErrorMessageDto()
                .code(webApiException.getCode() != null ? webApiException.getCode() :
                        String.valueOf(webApiException.getHttpStatus().value()))
                .devMessage(webApiException.getMessage())
                .userMessage(webApiException.getUserMessage());
    }

    //A single place to customize the response body
    public ResponseEntity<Object> toResponseEntity(WebApiException webApiException) {
        return ResponseEntity.status(webApiException.getHttpStatus()).body(
                toErrorMessageDto(webApiException));
    }
}
