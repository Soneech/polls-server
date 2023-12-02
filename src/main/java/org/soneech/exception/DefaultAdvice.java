package org.soneech.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class DefaultAdvice {
    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleAuthException(AuthException exception) {
        return defaultErrorMessage(exception.getStatus(), exception.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(UserNotFoundException exception) {
        return defaultErrorMessage(exception.getStatus(), exception.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handlePollNotFoundException(PollNotFoundException exception) {
        return defaultErrorMessage(exception.getStatus(), exception.getMessage());
    }

    public ResponseEntity<Map<String, String>> defaultErrorMessage(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(Map.of("message", message));
    }
}
