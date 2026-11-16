package com.smarthomerentalplatform.service.impl;

import ch.qos.logback.core.util.StringUtil;
import com.smarthomerentalplatform.exception.BadRequestException;
import com.smarthomerentalplatform.exception.ErrorInfo;
import com.smarthomerentalplatform.exception.ForbiddenException;
import com.smarthomerentalplatform.exception.code.DomainCode;
import com.smarthomerentalplatform.model.request.LoginRequest;
import com.smarthomerentalplatform.model.request.RegisterRequest;
import com.smarthomerentalplatform.model.response.LoginResponse;
import com.smarthomerentalplatform.model.response.UserResponse;
import com.smarthomerentalplatform.repository.UserRepository;
import com.smarthomerentalplatform.repository.entity.User;
import com.smarthomerentalplatform.repository.status.AccountStatus;
import com.smarthomerentalplatform.repository.status.UserRole;
import com.smarthomerentalplatform.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse register(RegisterRequest request) {
        log.info("Start to register user");

        validateRegistrationRequest(request);
        validateRegistrationRole(request.getRole());
        checkEmailExists(request.getEmail());

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = createUser(request, encodedPassword);
        User savedUser = userRepository.save(user);

        log.info("Register user successfully, userId={}", savedUser.getId());

        return UserResponse.builder()
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .fullName(savedUser.getFullName())
                .phone(savedUser.getPhone())
                .role(savedUser.getRole())
                .status(savedUser.getStatus())
                .build();
    }

    private void validateRegistrationRequest(RegisterRequest request) {
        if (request == null
                || StringUtil.isNullOrEmpty(request.getEmail())
                || StringUtil.isNullOrEmpty(request.getPassword())
                || StringUtil.isNullOrEmpty(request.getFullName())
                || StringUtil.isNullOrEmpty(request.getPhone())
                || request.getRole() == null) {

            throw new BadRequestException(new ErrorInfo(DomainCode.INVALID_CREDENTIALS));
        }
    }

    private void validateRegistrationRole(UserRole role) {
        if (role == UserRole.ADMIN) {
            throw new ForbiddenException(new ErrorInfo(DomainCode.ACCESS_DENIED));
        }
    }

    private void checkEmailExists(String email) {
        if (userRepository.existsByEmail(email.trim())) {
            throw new BadRequestException(new ErrorInfo(DomainCode.EMAIL_ALREADY_EXISTS));
        }
    }

    private User createUser(RegisterRequest request, String encodedPassword) {
        User user = new User();

        user.setEmail(request.getEmail().trim());
        user.setFullName(request.getFullName().trim());
        user.setPhone(request.getPhone().trim());
        user.setRole(request.getRole());
        user.setPassword(encodedPassword);

        if (request.getRole() == UserRole.ADMIN) {
            user.setStatus(AccountStatus.ACTIVE);
        } else {
            user.setStatus(AccountStatus.PENDING_APPROVAL);
        }

        return user;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        return null;
    }

    @Override
    public UserResponse getCurrentUser() {
        return null;
    }
}
