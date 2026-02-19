package com.ernoxin.boorsazmaapi.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("نام کاربری یا رمز عبور اشتباه است.");
    }
}
