package com.home365.airlines.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidArgumentException extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    public InvalidArgumentException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s should not have %s as the %s value because it's not valid.", resourceName, fieldValue, fieldName));
        log.warn(String.format("%s should not have %s as the %s value because it's not valid.", resourceName, fieldValue, fieldName));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
