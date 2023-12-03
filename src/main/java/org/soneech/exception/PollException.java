package org.soneech.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class PollException extends RuntimeException{
    private final HttpStatus status = HttpStatus.BAD_REQUEST;
    private List<String> errorMessages;

    public PollException(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }
}
