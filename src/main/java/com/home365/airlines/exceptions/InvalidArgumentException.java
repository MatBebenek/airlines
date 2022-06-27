package com.home365.airlines.exceptions;

import com.home365.airlines.controller.AircraftController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidArgumentException extends RuntimeException {

    private static final Logger LOG = LoggerFactory.getLogger(InvalidArgumentException.class);
    private String resourceName;
    private String fieldName;
    private Object fieldValue;

    public InvalidArgumentException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s should not have %s as the %s value. ", resourceName, fieldValue, fieldName));
        LOG.warn(String.format("%s should not have %s as the %s value. ", resourceName, fieldValue, fieldName));
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
