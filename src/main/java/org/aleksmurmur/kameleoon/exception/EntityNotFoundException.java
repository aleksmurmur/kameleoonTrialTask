package org.aleksmurmur.kameleoon.exception;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends BaseException{

    private final HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    public EntityNotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
