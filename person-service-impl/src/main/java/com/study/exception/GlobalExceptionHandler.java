package com.study.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundCrmException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessageDto handleNotFound(NotFoundCrmException ex) {
        log.error("Person not found: {}", ex.getMessage());

        ExceptionMessageDto data = new ExceptionMessageDto();
        data.setMessage(ex.getMessage());
        data.setStatus(HttpStatus.NOT_FOUND);

        return data;
    }
}