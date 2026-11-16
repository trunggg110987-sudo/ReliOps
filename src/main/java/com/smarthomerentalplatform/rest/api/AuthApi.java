package com.smarthomerentalplatform.rest.api;

import com.smarthomerentalplatform.model.request.RegisterRequest;
import com.smarthomerentalplatform.model.response.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Validated
@RequestMapping("/api/v1/auth")
public interface AuthApi {

    @PostMapping("/register")
    ResponseEntity<UserResponse> register(RegisterRequest request);
}
