package com.ernoxin.boorsazmaapi.exception;

public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String userMessage) {
        super(userMessage);
    }
}
