package spring_boot_debugging.exception;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    //@ExceptionHandler(MethodArgumentNotValidException.class)
    //@ResponseStatus(HttpStatus.BAD_REQUEST)
    //public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    //    ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
    //    problemDetail.setDetail("Bad Request");
    //    problemDetail.setDetail(ex.getMessage());
    //    return problemDetail;
    //}

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Validation Error");

        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .reduce((msg1, msg2) -> msg1 + ", " + msg2)
                .orElse("Invalid request");

        problemDetail.setDetail(errors);

        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ProblemDetail handleAllExceptions(Exception ex, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setTitle("Internal Server Error");
        problemDetail.setDetail(ex.getMessage());
        return problemDetail;
    }

    // Problème : Gestion trop générique des exceptions
    //@ExceptionHandler(Exception.class)
    //public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) {
    //    // Problème : Exposition des détails de l'exception dans la réponse
    //    ErrorDetails errorDetails = new ErrorDetails(
    //            new Date(),
    //            ex.getMessage(),
    //            request.getDescription(false),
    //            ex.toString()  // Problème : Exposition de la stack trace
    //    );
    //    return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    //}

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
