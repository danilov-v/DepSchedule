package com.varb.schedule.exception.handlers;

import com.varb.schedule.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Перенаправляет ошибки в {@link BusinessExceptionHandler}
 */
@Component
public class ExceptionResolver {
    private final HandlerExceptionResolver resolver;

    public ExceptionResolver(@Autowired @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    public void resolve(ServiceException serviceException, HttpServletRequest request, HttpServletResponse response) {
        resolver.resolveException(request, response, null, serviceException);
    }
}
