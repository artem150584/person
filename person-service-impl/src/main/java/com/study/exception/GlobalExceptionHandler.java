package com.study.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;

import static com.study.constants.ExceptionMessages.INVALID_ID_FORMAT;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundCrmException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleNotFound(NotFoundCrmException ex) {
        log.warn("Person not found: {}", ex.getMessage());

        return Map.of("error", ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        log.warn("Wrong UUID format {}", ex.getMessage());

        return Map.of("error", INVALID_ID_FORMAT + ex.getValue());
    }
}