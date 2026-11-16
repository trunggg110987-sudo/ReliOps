package com.smarthomerentalplatform.rest.controller;

import com.smarthomerentalplatform.model.request.RegisterRequest;
import com.smarthomerentalplatform.model.response.UserResponse;
import com.smarthomerentalplatform.rest.api.AuthApi;
import com.smarthomerentalplatform.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController implements AuthApi {

    private final AuthService authService;

    @Override
    public ResponseEntity<UserResponse> register(RegisterRequest request) {
        log.info("Register request with FullName : {}", request.getFullName());
        return ResponseEntity.ok(this.authService.register(request));
    }
}
