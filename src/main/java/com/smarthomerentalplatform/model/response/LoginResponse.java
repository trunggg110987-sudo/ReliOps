package com.smarthomerentalplatform.model.response;

public record LoginResponse(
        String accessToken,
        String tokenType,
        long expiresIn
) {
}