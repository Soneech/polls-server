package org.soneech.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class VoteDuplicateException extends RuntimeException {
    private final HttpStatus status = HttpStatus.BAD_REQUEST;
    public VoteDuplicateException() {
        super("Вы уже проголосовали");
    }
}
