package com.study.exception;

public class NotFoundCrmException extends RuntimeException {

    public NotFoundCrmException(String message) {
        super(message);
    }
}
