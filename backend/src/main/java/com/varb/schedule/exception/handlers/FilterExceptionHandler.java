package com.varb.schedule.exception.handlers;

import com.varb.schedule.exception.ServiceException;
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
 * либо не перехваченные в {@link BuisnessExceptionHandler} ошибки бизнес-логики
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class FilterExceptionHandler extends OncePerRequestFilter {
    private final JsonMapper jsonMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            if (ex instanceof ServiceException)
                buildServiceExceptionResponse((ServiceException) ex, response);
            else
                buildServiceExceptionResponse(new ServiceException(ex, HttpStatus.INTERNAL_SERVER_ERROR), response);
        }
    }

    private void buildServiceExceptionResponse(ServiceException serviceException, HttpServletResponse response) throws IOException {
        response.setStatus(serviceException.getHttpStatus().value());
        response.getWriter().write(jsonMapper.toString(ExceptionFormatter.toErrorMessageDto(serviceException)));
    }
}
