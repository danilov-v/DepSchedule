package com.varb.schedule.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * General class for all api exceptions
 */
public class WebApiException extends RuntimeException {
    /**
     * Response http-status
     */
    private final HttpStatus httpStatus;
    /**
     * Error message to present to user
     */
    @Nullable
    private final String userMessage;

    /**
     * Unique error code
     */
    @Nullable
    private final String code;

    /**
     * @param devMessage Error message to present to developer
     *                   <br>
     *                   HttpStatus = BAD_REQUEST
     */
    public WebApiException(String devMessage) {
        super(devMessage);
        userMessage = null;
        code = null;
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    /**
     * @param devMessage  Error message to present to developer
     * @param userMessage Error message to present to user
     * @param code        Unique error code
     *                    <br>
     *                    HttpStatus = BAD_REQUEST
     */
    public WebApiException(String devMessage, @NonNull String userMessage, @NonNull String code) {
        super(devMessage);
        this.userMessage = userMessage;
        this.code = code;
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    /**
     * @param devMessage  Error message to present to developer
     * @param userMessage Error message to present to user
     * @param code        Unique error code
     * @param httpStatus  Response http-status
     */
    public WebApiException(String devMessage, @NonNull String userMessage, @NonNull String code, HttpStatus httpStatus) {
        super(devMessage);
        this.userMessage = userMessage;
        this.code = code;
        this.httpStatus = httpStatus;
    }

    /**
     * @param devMessage Error message to present to developer
     * @param httpStatus Response http-status
     */
    public WebApiException(String devMessage, HttpStatus httpStatus) {
        super(devMessage);
        userMessage = null;
        code = null;
        this.httpStatus = httpStatus;
    }

//    public ServiceException(String message, int httpStatus) {
//        super(message);
//        userMessage = null;
//        code = null;
//        this.httpStatus = HttpStatus.valueOf(httpStatus);
//    }

    /**
     * @param cause      the cause (which is saved for later retrieval by the
     *                   {@link #getCause()} method).
     * @param httpStatus Response http-status
     */
    public WebApiException(Throwable cause, HttpStatus httpStatus) {
        super(cause.getMessage(), cause);
        userMessage = null;
        code = null;
        this.httpStatus = httpStatus;
    }

    /**
     * @param cause      the cause (which is saved for later retrieval by the
     *                   {@link #getCause()} method).
     * @param devMessage Error message to present to developer
     * @param httpStatus Response http-status
     */
    public WebApiException(Throwable cause, String devMessage, HttpStatus httpStatus) {
        super(devMessage, cause);
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
    public String getDevMessage() {
        return super.getMessage();
    }

    @Nullable
    public String getCode() {
        return code;
    }
}
