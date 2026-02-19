package com.ernoxin.boorsazmaapi.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String userMessage) {
        super(userMessage);
    }
}
