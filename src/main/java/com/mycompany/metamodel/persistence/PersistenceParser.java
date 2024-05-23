package com.mycompany.metamodel.persistence;

import com.mycompany.metamodel.pojo.DomainModel;
import com.mycompany.metamodel.pojo.ObjectDefinition;
import com.mycompany.metamodel.pojo.ParserResult;
import com.mycompany.metamodel.pojo.PropertyDefinition;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PersistenceParser {

    private DomainModel domainModel = DomainModel.getInstance();


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

    public static void main(String[] args) {
        Map<String, Column> original = new HashMap<>();
        original.put("foo", new Column());
        original.put("bar", new Column());

        Map<String, Columnx> copy = original.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> new Columnx(e.getValue().toString())));

        System.out.println(original);
        System.out.println(copy);
    }

    private ObjectDefinition processAnEntry(Map.Entry<String, Object> entry) {
        return parseAnObject(entry.getKey(), (Map<String, Object>) entry.getValue());
    }

    static class Column {
        public Column() {}
        public Column(Column c) {}
    }

    static class Columnx {
        public Columnx() {}
        public Columnx(String c) {}
    }
}
