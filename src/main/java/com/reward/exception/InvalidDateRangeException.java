package com.reward.exception;

public class InvalidDateRangeException extends RuntimeException {

    public final String message;

    public InvalidDateRangeException(String message) {
        super(message);
        this.message = message;
    }
}
