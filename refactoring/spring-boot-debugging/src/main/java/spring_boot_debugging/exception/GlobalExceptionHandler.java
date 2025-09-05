package spring_boot_debugging.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // Problème : Gestion trop générique des exceptions
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) {
        // Problème : Exposition des détails de l'exception dans la réponse
        ErrorDetails errorDetails = new ErrorDetails(
                new Date(),
                ex.getMessage(),
                request.getDescription(false),
                ex.toString()  // Problème : Exposition de la stack trace
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Problème : Pas de gestion spécifique pour les erreurs 404
    // Problème : Pas de gestion des erreurs de validation
    // Problème : Pas de gestion des erreurs d'authentification/autorisation

    public static class ErrorDetails {
        private Date timestamp;
        private String message;
        private String details;
        private String stackTrace;  // Problème : Exposition de la stack trace

        public ErrorDetails(Date timestamp, String message, String details, String stackTrace) {
            this.timestamp = timestamp;
            this.message = message;
            this.details = details;
            this.stackTrace = stackTrace;
        }

        // Problème : Pas de getters/setters
    }
}
