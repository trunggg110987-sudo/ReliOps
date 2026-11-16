package com.smarthomerentalplatform.service;

import com.smarthomerentalplatform.model.request.LoginRequest;
import com.smarthomerentalplatform.model.request.RegisterRequest;
import com.smarthomerentalplatform.model.response.LoginResponse;
import com.smarthomerentalplatform.model.response.UserResponse;

public interface AuthService {
    UserResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    UserResponse getCurrentUser();
}
