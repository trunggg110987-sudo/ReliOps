package com.smarthomerentalplatform.security;

import com.smarthomerentalplatform.exception.code.DomainCode;
import com.smarthomerentalplatform.repository.UserRepository;
import com.smarthomerentalplatform.repository.entity.User;
import com.smarthomerentalplatform.util.AppConstant;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    @NullMarked
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(AppConstant.USER_NOT_FOUND));
        return new CustomUserDetails(user);
    }
}
