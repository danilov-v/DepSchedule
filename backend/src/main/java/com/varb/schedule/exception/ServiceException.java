package com.varb.schedule.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

/**
 * Исключения сервисного слоя
 */
public class ServiceException extends RuntimeException {
    private final HttpStatus httpStatus;
    @Nullable
    private final String userMessage;
    @Nullable
    private final String code;

    /**
     * @param devMessage Сообщение об ошибке
     * <br>
     * HttpStatus = BAD_REQUEST
     */
    public ServiceException(String devMessage) {
        super(devMessage);
        userMessage = null;
        code = null;
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public ServiceException(String devMessage, String userMessage, String code) {
        super(devMessage);
        this.userMessage = userMessage;
        this.code = code;
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public ServiceException(String message, final HttpStatus httpStatus) {
        super(message);
        userMessage = null;
        code = null;
        this.httpStatus = httpStatus;
    }

    public ServiceException(final String message, final int httpStatus) {
        super(message);
        userMessage = null;
        code = null;
        this.httpStatus = HttpStatus.valueOf(httpStatus);
    }

    public ServiceException(final Throwable cause, final HttpStatus httpStatus) {
        super(cause.getMessage(), cause);
        userMessage = null;
        code = null;
        this.httpStatus = httpStatus;
    }

    public ServiceException(final Throwable cause, final String message, final HttpStatus httpStatus) {
        super(message, cause);
        userMessage = null;
        code = null;
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Nullable
    public String getUserMessage() {
        return userMessage;
    }

    @Nullable
    public String getCode() {
        return code;
    }
}
