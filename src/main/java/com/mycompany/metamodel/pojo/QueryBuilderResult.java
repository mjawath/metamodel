package com.mycompany.metamodel.pojo;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class QueryBuilderResult {

    private Map<String, BuilderResult> resultByName = new LinkedHashMap<>();

    @Data
    public static class BuilderResult {
        private String entityName;
        private ObjectDefinition objectDefinition;
        private Map<String, String> attributeByColumnName = new LinkedHashMap<>();
        private Map<String, PropertyDefinition> propertyDefinition = new LinkedHashMap<>();
        private Object queryObject;
    }
}

