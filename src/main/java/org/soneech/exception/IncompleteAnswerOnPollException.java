package org.soneech.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class IncompleteAnswerOnPollException extends RuntimeException {
    private final HttpStatus status = HttpStatus.BAD_REQUEST;

    public IncompleteAnswerOnPollException() {
        super("Сначала нужно ответить на все вопросы :)");
    }
}
