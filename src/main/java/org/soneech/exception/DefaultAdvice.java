package org.soneech.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;

@ControllerAdvice
public class DefaultAdvice {
    @ExceptionHandler
    public ResponseEntity<Map<String, List<String>>> handleAuthException(AuthException exception) {
        return ResponseEntity.status(exception.getStatus()).body(Map.of("messages", exception.getErrorMessages()));
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(UserNotFoundException exception) {
        return defaultErrorMessage(exception.getStatus(), exception.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handlePollNotFoundException(PollNotFoundException exception) {
        return defaultErrorMessage(exception.getStatus(), exception.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleVoteDuplicateException(VoteDuplicateException exception) {
        return defaultErrorMessage(exception.getStatus(), exception.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, List<String>>> handlePollException(PollException exception) {
        return ResponseEntity.status(exception.getStatus()).body(Map.of("messages", exception.getErrorMessages()));
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleIncompleteAnswerOnPollException(
                                                                        IncompleteAnswerOnPollException exception) {
        return defaultErrorMessage(exception.getStatus(), exception.getMessage());
    }

    public ResponseEntity<Map<String, String>> defaultErrorMessage(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(Map.of("message", message));
    }
}
