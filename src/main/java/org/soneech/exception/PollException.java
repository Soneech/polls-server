package org.soneech.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PollException extends RuntimeException{
    private HttpStatus status = HttpStatus.BAD_REQUEST;

    public PollException(String message) {
        super(message);
    }
}
