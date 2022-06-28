package com.home365.airlines.exception;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class NotEnoughResourcesException extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    public NotEnoughResourcesException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s have only %s of %s so it's not enough.", resourceName, fieldValue, fieldName));
        log.warn(String.format("%s have only %s of %s so it's not enough.", resourceName, fieldValue, fieldName));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
