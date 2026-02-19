package com.ernoxin.boorsazmaapi.controller;

import com.ernoxin.boorsazmaapi.dto.api.ApiResponse;
import com.ernoxin.boorsazmaapi.dto.auth.AuthTokenResponse;
import com.ernoxin.boorsazmaapi.dto.auth.LoginRequest;
import com.ernoxin.boorsazmaapi.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ApiResponse<AuthTokenResponse> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.of(HttpStatus.OK, "عملیات با موفقیت انجام شد", authService.login(request));
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout() {
        authService.logout();
        return ApiResponse.of(HttpStatus.OK, "عملیات با موفقیت انجام شد", null);
    }
}
