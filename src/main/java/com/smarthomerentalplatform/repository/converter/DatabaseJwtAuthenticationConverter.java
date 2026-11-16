package com.smarthomerentalplatform.repository.converter;


import com.smarthomerentalplatform.repository.UserRepository;
import com.smarthomerentalplatform.repository.entity.User;
import com.smarthomerentalplatform.repository.status.AccountStatus;
import com.smarthomerentalplatform.security.CustomUserDetails;
import com.smarthomerentalplatform.util.AppConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseJwtAuthenticationConverter
        implements Converter<Jwt, AbstractAuthenticationToken> {

    private final UserRepository userRepository;

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Long userId;

        try {
            userId = Long.valueOf(jwt.getSubject());
        } catch (NumberFormatException ex) {
            throw invalidToken();
        }

        User user = userRepository.findById(userId)
                .orElseThrow(this::invalidToken);

        if (user.getStatus() != AccountStatus.ACTIVE) {
            throw invalidToken();
        }

        CustomUserDetails userDetails = new CustomUserDetails(user);

        return new UsernamePasswordAuthenticationToken(
                userDetails,
                jwt,
                userDetails.getAuthorities()
        );
    }

    private OAuth2AuthenticationException invalidToken() {
        return new OAuth2AuthenticationException(new OAuth2Error(AppConstant.INVALID_TOKEN));
    }
}

