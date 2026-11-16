package com.smarthomerentalplatform.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class ForwardedErrorCode implements ErrorCode {
    private final String code;
    private final String message;
}
