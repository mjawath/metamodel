package com.mycompany.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;

public class JsonRead {
    public static <T> T readJsonFromResource(String resourcePath, Class<T> valueType) {
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath)) {
            return mapper.readValue(in, valueType);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse resource: " + resourcePath, e);
        }
    }
}
