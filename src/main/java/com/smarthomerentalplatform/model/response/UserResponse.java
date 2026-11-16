package com.smarthomerentalplatform.model.response;

import com.smarthomerentalplatform.repository.status.AccountStatus;
import com.smarthomerentalplatform.repository.status.UserRole;
import lombok.Builder;

@Builder
public record UserResponse(
        Long id,
        String email,
        String fullName,
        String phone,
        UserRole role,
        AccountStatus status
) {
}
