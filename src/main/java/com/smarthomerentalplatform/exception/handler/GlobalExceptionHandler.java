package com.smarthomerentalplatform.exception.handler;

import com.smarthomerentalplatform.exception.*;
import com.smarthomerentalplatform.exception.code.DomainCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorInfo> handleBadRequest(BadRequestException exception) {
        return ResponseEntity.badRequest().body(exception.getErrorInfo());
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorInfo> handleInternalServerError(InternalServerErrorException exception) {
        return ResponseEntity.internalServerError().body(exception.getErrorInfo());
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorInfo> handleServiceException(ServiceException exception) {
        log.error("Unexpected exception occurred", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getErrorInfo());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> handleUnexpectedException(Exception exception) {
        ErrorInfo errorInfo = new ErrorInfo(
                DomainCode.INTERNAL_SERVICE_ERROR,
                Map.of("exceptionType", exception.getClass().getSimpleName()),
                java.util.List.of()
        );
        return ResponseEntity.internalServerError().body(errorInfo);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorInfo> handleUnauthorizedException(UnauthorizedException exception) {
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getErrorInfo());
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorInfo> handleForbiddenException(ForbiddenException exception) {
        return  ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.getErrorInfo());
    }
}
