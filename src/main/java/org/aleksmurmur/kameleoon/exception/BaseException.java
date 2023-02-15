package org.aleksmurmur.kameleoon.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BaseException extends RuntimeException {

    private final String message;
    private final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    public BaseException(String message) {
        this.message = message;
    }


}
