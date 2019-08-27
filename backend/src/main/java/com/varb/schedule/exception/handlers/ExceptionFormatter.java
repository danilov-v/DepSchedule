package com.varb.schedule.exception.handlers;

import com.varb.schedule.buisness.models.dto.ErrorMessageDto;
import com.varb.schedule.exception.ServiceException;
import com.varb.schedule.utils.JsonMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExceptionFormatter {
    private final JsonMapper jsonMapper;

    //A single place to customize the response body
    private ErrorMessageDto toErrorMessageDto(ServiceException serviceException) {
        return new ErrorMessageDto()
                .code(serviceException.getCode() != null ? serviceException.getCode() :
                        String.valueOf(serviceException.getHttpStatus().value()))
                .devMessage(serviceException.getMessage())
                .userMessage(serviceException.getUserMessage());
    }

    public ResponseEntity<Object> toResponseEntity(ServiceException serviceException) {
        return ResponseEntity.status(serviceException.getHttpStatus()).body(
                toErrorMessageDto(serviceException));
    }

    public void toHttpServletResponse(HttpServletResponse response, ServiceException serviceException) throws IOException {
        response.setStatus(serviceException.getHttpStatus().value());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(jsonMapper.toString(toErrorMessageDto(serviceException)));
    }
}
