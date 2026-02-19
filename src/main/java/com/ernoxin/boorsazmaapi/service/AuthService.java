package com.ernoxin.boorsazmaapi.service;

import com.ernoxin.boorsazmaapi.dto.auth.AuthTokenResponse;
import com.ernoxin.boorsazmaapi.dto.auth.LoginRequest;

public interface AuthService {
    AuthTokenResponse login(LoginRequest request);

    void logout();
}
