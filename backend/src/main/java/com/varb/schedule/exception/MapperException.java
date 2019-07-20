package com.varb.schedule.exception;

/**
 * Исключения мапперов
 */
public class MapperException extends RuntimeException {
    public MapperException(final Throwable cause) {
        super(cause);
    }
    public MapperException(final Throwable cause, final String source) {
        super(cause.getMessage() + "\n" +"          Full source = " + source, cause);
    }
}
