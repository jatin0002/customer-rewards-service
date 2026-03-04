package com.reward.exception;

public class UserNotFoundException extends RuntimeException {

    public final String message;

    public UserNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
