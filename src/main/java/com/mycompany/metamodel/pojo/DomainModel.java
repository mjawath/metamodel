package com.mycompany.metamodel.pojo;

import java.util.LinkedHashMap;
import java.util.Map;

public class DomainModel {

    private static DomainModel instance= new DomainModel();

    private Map<String, ObjectDefinition> objects= new LinkedHashMap<>();

    public static DomainModel getInstance() {
        return instance;
    }

    public Map<String, ObjectDefinition> getObjects() {
        return objects;
    }

    public void setObjects(Map<String, ObjectDefinition> objects) {
        this.objects = objects;
    }

    public ObjectDefinition getObjectDefinition(String objectName) {
        return objects.get(objectName);
    }
}