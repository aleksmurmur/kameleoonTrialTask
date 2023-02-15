package org.aleksmurmur.kameleoon.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseException{

    private final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
    public BadRequestException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
