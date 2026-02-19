package com.ernoxin.boorsazmaapi.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "ایمیل یا شماره موبایل نباید خالی باشد.")
    private String identifier;

    @NotBlank(message = "رمز عبور نباید خالی باشد.")
    @Size(min = 8, max = 24, message = "رمز عبور باید بین ۸ تا ۲۴ کاراکتر باشد.")
    private String password;
}
