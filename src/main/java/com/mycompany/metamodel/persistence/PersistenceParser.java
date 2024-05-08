package com.mycompany.metamodel.persistence;

import com.mycompany.metamodel.pojo.DomainModel;
import com.mycompany.metamodel.pojo.ObjectDefinition;
import com.mycompany.metamodel.pojo.PropertyDefinition;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class PersistenceParser {

    private DomainModel domainModel = DomainModel.getInstance();

    public ObjectDefinition parseAnObject(String entityName, Map<String, Object> objectMap) {
        ObjectDefinition metamodelPersistenceObject = new ObjectDefinition();
        metamodelPersistenceObject.setEntityName(entityName);

        ObjectDefinition originalMetaObjectDef = domainModel.getObjectDefinition(entityName);
        if (originalMetaObjectDef == null) {
            throw new IllegalArgumentException("Object definition not found for entity: " + entityName);
        }
        metamodelPersistenceObject.setTableName(originalMetaObjectDef.getTableName()); // Set table name based on entity type
        Map<String, PropertyDefinition> propertyDefinitions = new LinkedHashMap<>();
        Object o = objectMap.get(entityName);
        if (!(o instanceof Map)){
            return null;
        }
        for (Map.Entry<String, Object> entry : ((Map<String, Object>)o).entrySet()) {
            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();
            PropertyDefinition propertyDefinition = originalMetaObjectDef.getPropertyDefinition(fieldName);
            propertyDefinition = propertyDefinition.clone();
            propertyDefinition.setValue(fieldValue);
            propertyDefinitions.put(fieldName, propertyDefinition);
        }
        ObjectDefinition definition = originalMetaObjectDef.clone();
        definition.setProperties(propertyDefinitions);
        return definition;
    }

    public ObjectDefinition parseObjects(String entityName, Map<String, Object> objectMap) {
        ObjectDefinition metamodelPersistenceObject = new ObjectDefinition();
        metamodelPersistenceObject.setEntityName(entityName);

        ObjectDefinition originalMetaObjectDef = domainModel.getObjectDefinition(entityName);
        if (originalMetaObjectDef == null) {
            throw new IllegalArgumentException("Object definition not found for entity: " + entityName);
        }
        metamodelPersistenceObject.setTableName(originalMetaObjectDef.getTableName()); // Set table name based on entity type
        Map<String, PropertyDefinition> propertyDefinitions = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();
            PropertyDefinition propertyDefinition = originalMetaObjectDef.getPropertyDefinition(fieldName);
            propertyDefinition = propertyDefinition.clone();
            propertyDefinition.setValue(fieldValue);
            propertyDefinitions.put(fieldName, propertyDefinition);
        }
        ObjectDefinition definition = originalMetaObjectDef.clone();
        definition.setProperties(propertyDefinitions);
        return definition;
    }
}
