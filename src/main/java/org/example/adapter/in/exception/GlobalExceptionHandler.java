package org.example.adapter.in.exception;

import org.example.application.exception.AccountNotFoundException;
import org.example.domain.exception.InvalidWithdrawalAmountException;
import org.example.domain.exception.OverdraftLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidWithdrawalAmountException.class)
    public ResponseEntity<Map<String, String>> handleInvalidWithdrawal(InvalidWithdrawalAmountException ex) {
      return buildErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
    }

    @ExceptionHandler(OverdraftLimitExceededException.class)
    public ResponseEntity<Map<String, String>> handleOverdraftLimitExceeded(OverdraftLimitExceededException ex) {
      return buildErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // (optionnel) gestion générique d'erreurs inattendues
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleOtherExceptions(Exception ex) {
      return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Une erreur interne est survenue");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleInvalidFormat(HttpMessageNotReadableException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Format invalide dans la requête : " + ex.getMostSpecificCause().getMessage());
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleAccountNotFoundException(AccountNotFoundException ex) {
      return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    private ResponseEntity<Map<String, String>> buildErrorResponse(HttpStatus status, String message) {
      Map<String, String> error = new HashMap<>();
      error.put("message", message);
      return ResponseEntity.status(status).body(error);
    }
}
