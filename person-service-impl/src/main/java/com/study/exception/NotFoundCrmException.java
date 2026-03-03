package com.study.exception;

import java.util.UUID;

public class NotFoundCrmException extends RuntimeException {

    public NotFoundCrmException(String message) {
        super(message);
    }
}
