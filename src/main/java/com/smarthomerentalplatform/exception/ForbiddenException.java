package com.smarthomerentalplatform.exception;

public class ForbiddenException extends ServiceException {

    public ForbiddenException(ErrorInfo errorInfo) {
        super(errorInfo);
    }

    public ForbiddenException(ErrorInfo errorInfo, Throwable cause) {
        super(errorInfo, cause);
    }
}