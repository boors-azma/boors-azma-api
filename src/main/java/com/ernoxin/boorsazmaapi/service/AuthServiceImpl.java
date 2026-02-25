package com.ernoxin.boorsazmaapi.service;

import com.ernoxin.boorsazmaapi.dto.UserCreateRequest;
import com.ernoxin.boorsazmaapi.dto.auth.AuthTokenResponse;
import com.ernoxin.boorsazmaapi.dto.auth.LoginRequest;
import com.ernoxin.boorsazmaapi.exception.InvalidCredentialsException;
import com.ernoxin.boorsazmaapi.model.User;
import com.ernoxin.boorsazmaapi.repository.UserRepository;
import com.ernoxin.boorsazmaapi.security.AppUserPrincipal;
import com.ernoxin.boorsazmaapi.security.JwtTokenService;
import com.ernoxin.boorsazmaapi.security.RevokedTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private final RevokedTokenService revokedTokenService;

    @Override
    @Transactional
    public AuthTokenResponse register(UserCreateRequest request) {
        userService.create(request);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setIdentifier(request.getUsername());
        loginRequest.setPassword(request.getPassword());
        return login(loginRequest);
    }

    @Override
    @Transactional
    public AuthTokenResponse login(LoginRequest request) {
        String normalizedIdentifier = request.getIdentifier().trim().toLowerCase(Locale.ROOT);
        User user = userRepository.findByUsernameOrEmail(normalizedIdentifier, normalizedIdentifier)
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
    public void logout(String bearerToken) {
        if (bearerToken != null && jwtTokenService.isValid(bearerToken)) {
            revokedTokenService.revoke(bearerToken, jwtTokenService.extractExpiration(bearerToken));
        }
        SecurityContextHolder.clearContext();
    }
}
