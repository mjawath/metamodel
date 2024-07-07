package com.mycompany.metamodel.persistence;

import com.mycompany.metamodel.pojo.DomainModel;
import com.mycompany.metamodel.pojo.ObjectDefinition;
import com.mycompany.metamodel.pojo.ParserResult;
import com.mycompany.metamodel.pojo.PropertyDefinition;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PersistenceParser {

    private DomainModel domainModel = DomainModel.getInstance();

    public Map parse(Map<String, Map<String, Object>> objectMap) {
        Map<String, Object> entryMsp = new HashMap<>();

        for (Map.Entry<String, Map<String, Object>> entry : objectMap.entrySet()) {
            ObjectDefinition foundObj = domainModel.getObjectDefinition(entry.getKey());
            if (foundObj == null) {
                return null;
            } else if (entry.getValue() instanceof Map) {
                //for each
                Map<String, Object> objVal = entry.getValue();
                entryMsp.put(entry.getKey(), objVal);
                for (Map.Entry<String, Object> valueByAttr : objVal.entrySet()) {

                }

//                result.put(entry.getKey(), processAnEntry(entry));
            }
        }
        return null;
    }


    public ObjectDefinition parseAnObject(String entityName, Map<String, Object> objectMap) {
        ObjectDefinition found = new ObjectDefinition();
        found.setEntityName(entityName);

        ObjectDefinition originalMetaObjectDef = domainModel.getObjectDefinition(entityName);
        if (originalMetaObjectDef == null) {
            throw new IllegalArgumentException("Object definition not found for entity: " + entityName);
        }
        found.setTableName(originalMetaObjectDef.getTableName()); // Set table name based on entity type
        Map<String, PropertyDefinition> propertyDefinitions = new LinkedHashMap<>();
        Map<String, Object> aObj = new HashMap<>();
        setIdColumn(objectMap, originalMetaObjectDef, propertyDefinitions);
        for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();
            PropertyDefinition propertyDefinition = originalMetaObjectDef.getPropertyDefinition(fieldName);
            propertyDefinition = propertyDefinition.clone();
            propertyDefinition.setValue(fieldValue);
            propertyDefinitions.put(fieldName, propertyDefinition);
            aObj.put(fieldName, fieldValue);
        }
        ObjectDefinition definition = originalMetaObjectDef.clone();
        definition.setProperties(propertyDefinitions);
//        definition.setValueBag(aObj);
        return definition;
    }

    private static void setIdColumn(Map<String, Object> objectMap, ObjectDefinition originalMetaObjectDef, Map<String, PropertyDefinition> propertyDefinitions) {
        PropertyDefinition id = originalMetaObjectDef.getProperties().get("id");
        if(id !=null &&
                objectMap.get("id") == null){
            //TODO how to check different strategy to create id
            PropertyDefinition cloneid = id.clone();
            Clock clock = Clock.systemDefaultZone();
            Instant instant = clock.instant();   // or Instant.now();
            long seconds = instant.getEpochSecond();
            long nano = instant.getNano();
            cloneid.setValue(seconds+nano);
            propertyDefinitions.put("id", cloneid);
        }
    }

    public ParserResult parseObjects(Map<String, Object> objectsByName) {
        ParserResult parserResult = new ParserResult();

        Map<String, ObjectDefinition> objectMap = objectsByName.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, this::processAnEntry));
        parserResult.setObjectDefinitionMap(objectMap);
        return parserResult;
    }



    private ObjectDefinition processAnEntry(Map.Entry<String, Object> entry) {
        return parseAnObject(entry.getKey(), (Map<String, Object>) entry.getValue());
    }


}
