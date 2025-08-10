package org.example.domain.exception;

public class OverdraftLimitExceededException extends RuntimeException {
    public OverdraftLimitExceededException(String message) {
        super(message);
    }
}
