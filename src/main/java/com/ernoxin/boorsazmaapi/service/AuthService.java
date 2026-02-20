package com.ernoxin.boorsazmaapi.service;

import com.ernoxin.boorsazmaapi.dto.UserCreateRequest;
import com.ernoxin.boorsazmaapi.dto.auth.AuthTokenResponse;
import com.ernoxin.boorsazmaapi.dto.auth.LoginRequest;

public interface AuthService {
    AuthTokenResponse register(UserCreateRequest request);

    AuthTokenResponse login(LoginRequest request);

    void logout(String bearerToken);
}
