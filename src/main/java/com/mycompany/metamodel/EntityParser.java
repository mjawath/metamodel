package com.mycompany.metamodel;

import com.mycompany.metamodel.pojo.DomainModel;
import com.mycompany.metamodel.pojo.ObjectDefinition;
import com.mycompany.metamodel.pojo.PropertyDefinition;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EntityParser {

    private DomainModel domainModel = DomainModel.getInstance();

    public ObjectDefinition parseToMetamodelPersistenceObject(String entityName, Map<String, Object> objectMap) {
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
            propertyDefinition =  propertyDefinition.clone();
            propertyDefinition.setValue(fieldValue);
            propertyDefinitions.put(fieldName,propertyDefinition);
        }
        ObjectDefinition definition = originalMetaObjectDef.clone();
        definition.setProperties(propertyDefinitions);
        return definition;
    }

}

