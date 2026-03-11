package com.study.exception;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class ExceptionMessageDto {
    private String message;
    private HttpStatus status;
}
