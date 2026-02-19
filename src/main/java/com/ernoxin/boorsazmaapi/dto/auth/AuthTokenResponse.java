package com.ernoxin.boorsazmaapi.dto.auth;

import com.ernoxin.boorsazmaapi.model.UserRole;

import java.time.Instant;

public record AuthTokenResponse(
        String accessToken,
        Instant accessTokenExpiresAt,
        Long userId,
        UserRole role
) {
}
