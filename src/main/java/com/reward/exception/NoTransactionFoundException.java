package com.reward.exception;

public class NoTransactionFoundException extends RuntimeException {

    public final String message;

    public NoTransactionFoundException(String message) {
        super(message);
        this.message = message;
    }
}
