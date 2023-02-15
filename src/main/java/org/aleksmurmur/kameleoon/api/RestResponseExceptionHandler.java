package org.aleksmurmur.kameleoon.api;

import jakarta.servlet.http.HttpServletRequest;
import org.aleksmurmur.kameleoon.common.dto.ErrorResponse;
import org.aleksmurmur.kameleoon.common.dto.ValidationError;
import org.aleksmurmur.kameleoon.exception.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@ControllerAdvice
public class RestResponseExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestResponseExceptionHandler.class);

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> baseExceptionHandler(BaseException exception, HttpServletRequest request) {
        log(exception.getMessage(), request);
        return ResponseEntity.status(exception.getHttpStatus()).body(new ErrorResponse(exception.getHttpStatus().name(), exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleArgumentNotValidException (MethodArgumentNotValidException exception, HttpServletRequest request) {
        log(exception.getMessage(), request);
        var bindingResult = exception.getBindingResult();
        var fieldErrors = bindingResult.getFieldErrors().stream()
                .map( fieldError -> new ValidationError(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(
                HttpStatus.BAD_REQUEST.name(),
                fieldErrors.toString()
        ));
    }

    private void log(String message, HttpServletRequest request) {
        logger.warn(
                String.format(""" 
    |exception: %s
    |request method: %s
    |request uri: %s
            """, message, request.getMethod(), request.getRequestURI()
                ));
    }
}
