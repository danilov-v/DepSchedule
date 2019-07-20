package com.varb.schedule.exception;

import com.varb.schedule.buisness.models.dto.ErrorMessageDto;
import com.varb.schedule.utils.JsonMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Перехватывает ошибки, порождённые в фильтрах
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    private final JsonMapper jsonMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            //log.error(ex.getMessage(), ex);
            if (ex instanceof ServiceException)
                buildServiceExceptionResponse((ServiceException) ex, response);
            else
                buildUnexpectedExceptionResponse(ex, response);
        }
    }

    private void buildUnexpectedExceptionResponse(Exception ex, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.getWriter().write(jsonMapper.toString(
                new ErrorMessageDto()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                        .devMessage(ex.getMessage())
        ));
    }

    private void buildServiceExceptionResponse(ServiceException serviceException, HttpServletResponse response) throws IOException {
        response.setStatus(serviceException.getHttpStatus().value());
        response.getWriter().write(jsonMapper.toString(
                new ErrorMessageDto()
                        .code(serviceException.getCode() != null ? serviceException.getCode() :
                                String.valueOf(serviceException.getHttpStatus().value()))
                        .devMessage(serviceException.getMessage())
                        .userMessage(serviceException.getUserMessage())));
    }
}
