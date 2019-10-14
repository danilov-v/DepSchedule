package com.varb.schedule.exception.handlers;

import com.varb.schedule.exception.WebApiException;
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
 * Перехватывает ошибки в фильтрах
 * и перенаправляет в {@link BusinessExceptionHandler}
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class FilterExceptionHandler extends OncePerRequestFilter {
    private final ExceptionResolver exceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (WebApiException webApiException) {
            buildServiceExceptionResponse(webApiException, request, response);
        } catch (Exception ex) {
            buildServiceExceptionResponse(new WebApiException(ex, HttpStatus.INTERNAL_SERVER_ERROR), request, response);
        }
    }

    private void buildServiceExceptionResponse(WebApiException webApiException, HttpServletRequest request, HttpServletResponse response) throws IOException {
        exceptionResolver.resolve(webApiException, request, response);
    }
}
