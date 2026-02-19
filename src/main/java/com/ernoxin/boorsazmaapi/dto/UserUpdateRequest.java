package com.ernoxin.boorsazmaapi.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserUpdateRequest {

    @NotNull(message = "شناسه کاربر نباید خالی باشد.")
    @Positive(message = "شناسه کاربر باید عددی مثبت باشد.")
    private Long id;

    @NotBlank(message = "نام نباید خالی باشد.")
    private String firstName;

    @NotBlank(message = "نام خانوادگی نباید خالی باشد.")
    private String lastName;

    @NotBlank(message = "کد ملی نباید خالی باشد.")
    @Pattern(regexp = "^\\d{10}$", message = "کد ملی باید دقیقا ۱۰ رقم باشد.")
    private String nationalCode;

    @NotBlank(message = "شماره موبایل نباید خالی باشد.")
    @Pattern(regexp = "^\\+98\\d{10}$", message = "شماره موبایل باید با +98 شروع شود و ۱۰ رقم بعد از آن داشته باشد.")
    private String phoneNumber;

    @NotBlank(message = "ایمیل نباید خالی باشد.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@gmail\\.com$", message = "ایمیل باید از نوع gmail.com باشد.")
    private String email;

    @Size(min = 8, max = 24, message = "رمز عبور باید بین ۸ تا ۲۴ کاراکتر باشد.")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$",
            message = "رمز عبور باید حداقل شامل یک حرف و یک عدد باشد."
    )
    private String password;
}
