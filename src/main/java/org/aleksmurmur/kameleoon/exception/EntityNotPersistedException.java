package org.aleksmurmur.kameleoon.exception;

import org.springframework.http.HttpStatus;

public class EntityNotPersistedException extends BaseException{

    public EntityNotPersistedException(String message) {
        super(message);
    }
}
