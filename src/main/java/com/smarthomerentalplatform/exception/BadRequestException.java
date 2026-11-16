package com.smarthomerentalplatform.exception;

public class BadRequestException extends ServiceException {
    public BadRequestException(ErrorInfo errorInfo) {
        super(errorInfo);
    }

    public BadRequestException(ErrorInfo errorInfo, Throwable cause) {
        super(errorInfo, cause);
    }
}
