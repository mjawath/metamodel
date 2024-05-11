package com.mycompany.metamodel.pojo;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class ParserResult {

    private Map<String, ObjectDefinition> objectDefinitionMap;

    public void addObjectDefinition(ObjectDefinition objectDefinition) {
        if (objectDefinitionMap == null) {
            objectDefinitionMap = new LinkedHashMap<>();
        }
        objectDefinitionMap.put(objectDefinition.getEntityName(), objectDefinition);
    }
}
