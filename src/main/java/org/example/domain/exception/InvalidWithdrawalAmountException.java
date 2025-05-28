package org.example.domain.exception;

public class InvalidWithdrawalAmountException extends RuntimeException {
    public InvalidWithdrawalAmountException(String message) {
        super(message);
    }
}
