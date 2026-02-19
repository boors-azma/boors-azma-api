package com.ernoxin.boorsazmaapi.service;

import com.ernoxin.boorsazmaapi.dto.auth.AuthTokenResponse;
import com.ernoxin.boorsazmaapi.dto.auth.LoginRequest;
import com.ernoxin.boorsazmaapi.exception.InvalidCredentialsException;
import com.ernoxin.boorsazmaapi.model.User;
import com.ernoxin.boorsazmaapi.repository.UserRepository;
import com.ernoxin.boorsazmaapi.security.AppUserPrincipal;
import com.ernoxin.boorsazmaapi.security.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;

    @Override
    @Transactional
    public AuthTokenResponse login(LoginRequest request) {
        User user = userRepository.findByEmailOrPhoneNumber(request.getIdentifier(), request.getIdentifier())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        AppUserPrincipal principal = AppUserPrincipal.from(user);
        String accessToken = jwtTokenService.generateAccessToken(principal);
        Instant accessExpiresAt = jwtTokenService.accessTokenExpiresAt();

        return new AuthTokenResponse(
                accessToken,
                accessExpiresAt,
                user.getId(),
                user.getRole()
        );
    }

    @Override
    public void logout() {
        SecurityContextHolder.clearContext();
    }
}
