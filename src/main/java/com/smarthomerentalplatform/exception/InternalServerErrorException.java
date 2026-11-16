package com.smarthomerentalplatform.exception;

public class InternalServerErrorException extends ServiceException {
    public InternalServerErrorException(ErrorInfo errorInfo) {
        super(errorInfo);
    }

    public InternalServerErrorException(ErrorInfo errorInfo, Throwable cause) {
        super(errorInfo, cause);
    }
}
