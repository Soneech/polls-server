package org.soneech.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class DefaultAdvice {
    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleUserException(AuthException authException) {
        return new ResponseEntity<>(Map.of("message", authException.getMessage()), authException.getStatus());
    }
}
