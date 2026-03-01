package com.study.exception;

import java.util.UUID;

public class NotFoundCrmException extends RuntimeException {

    public NotFoundCrmException(UUID id) {
        super("Объект с ID [" + id + "] отсутствует в базе данных");
    }
}
