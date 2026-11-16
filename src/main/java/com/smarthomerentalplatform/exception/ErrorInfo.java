package com.smarthomerentalplatform.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smarthomerentalplatform.exception.code.ErrorCode;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

public record ErrorInfo(@JsonIgnore ErrorCode errorCode, Map<String, Object> additions, List<Object> args) {
    public ErrorInfo(ErrorCode errorCode) {
        this(errorCode, Map.of(), List.of());
    }

    public ErrorInfo(ErrorCode errorCode, Map<String, Object> additions, List<Object> args) {
        this.errorCode = errorCode;
        this.additions = additions == null ? Map.of() : Map.copyOf(additions);
        this.args = args == null ? List.of() : List.copyOf(args);
    }

    public String getCode() {
        return errorCode.getCode();
    }

    public String getMessage() {
        if (args.isEmpty()) {
            return errorCode.getMessage();
        }
        return MessageFormat.format(errorCode.getMessage(), args.toArray());
    }
}
