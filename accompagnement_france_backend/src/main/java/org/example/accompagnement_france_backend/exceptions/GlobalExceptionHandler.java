package org.example.accompagnement_france_backend.exceptions;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        Map<String, String> fieldErrors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        fieldErrors.put(error.getField(), error.getDefaultMessage())
                );

        return ResponseEntity
                .badRequest()
                .body(
                        Map.of(
                                "status", "error",
                                "message", "Erreur de validation",
                                "errors", fieldErrors
                        )
                );
    }
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex, HttpServletResponse response) {
        if (response.isCommitted()) {
            return null;
        }
        // Log the stack trace for debugging on the server side
        log.error("Backend Runtime Exception: ", ex);
        // Return a generic, non-PII, JSON response to the client
        return new ResponseEntity<>("{\"error\": \"An internal server error occurred.\"}",
                HttpStatus.INTERNAL_SERVER_ERROR); // 500
    }

    // 2. Handling the base Exception class (catch-all)
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleAllExceptions(Exception ex) {
        log.error("Unforeseen server exception:", ex);
        return new ResponseEntity<>("{\"error\": \"Unforeseen server issue.\"}",
                HttpStatus.INTERNAL_SERVER_ERROR); // 500
    }

    // 3. Optional: Example of handling a specific business error (e.g., if Demand is not found)
    // You would define a custom exception like DemandNotFoundException
    // @ExceptionHandler(DemandNotFoundException.class)
    // @ResponseStatus(HttpStatus.NOT_FOUND)
    // public ResponseEntity<String> handleNotFound(DemandNotFoundException ex) {
    //     return new ResponseEntity<>("{\"error\": \"" + ex.getMessage() + "\"}", HttpStatus.NOT_FOUND); // 404
    // }
}
