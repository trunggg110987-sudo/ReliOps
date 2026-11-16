package com.smarthomerentalplatform.exception;

public class UnauthorizedException extends ServiceException {

    public UnauthorizedException(ErrorInfo errorInfo) {
        super(errorInfo);
    }

    public UnauthorizedException(ErrorInfo errorInfo, Throwable cause) {
        super(errorInfo, cause);
    }
}