package com.home365.airlines.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class NotEnoughResourcesException extends RuntimeException {

    private static final Logger LOG = LoggerFactory.getLogger(NotEnoughResourcesException.class);
    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    public NotEnoughResourcesException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s have only %s of %s so it's not enough.", resourceName, fieldValue, fieldName));
        LOG.warn(String.format("%s have only %s of %s so it's not enough.", resourceName, fieldValue, fieldName));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}
