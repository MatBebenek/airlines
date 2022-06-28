package com.home365.airlines.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MapperHelper {
    private MapperHelper() {}

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String asJsonString(Object object) {
        objectMapper.findAndRegisterModules();
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException jsonProcessingException) {
            throw new RuntimeException(jsonProcessingException);
        }
    }

}