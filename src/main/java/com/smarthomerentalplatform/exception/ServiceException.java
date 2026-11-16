package com.smarthomerentalplatform.exception;

import lombok.Getter;

@Getter
public abstract class ServiceException extends RuntimeException {
    private final ErrorInfo errorInfo;

    protected ServiceException(ErrorInfo errorInfo) {
        super(errorInfo.getMessage());
        this.errorInfo = errorInfo;
    }

    protected ServiceException(ErrorInfo errorInfo, Throwable cause) {
        super(errorInfo.getMessage(), cause);
        this.errorInfo = errorInfo;
    }

}
