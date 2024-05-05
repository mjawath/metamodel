package com.mycompany.metamodel;

import java.util.Map;

public class EntityParser {
    public static MetamodelPersistenceObject parseToMetamodelPersistenceObject(Map<String, Object> objectMap) {
        MetamodelPersistenceObject metamodelPersistenceObject = new MetamodelPersistenceObject();
        // Parse objectMap and populate metamodelPersistenceObject
        // You may need to customize this based on your specific requirements
        return metamodelPersistenceObject;
    }

    public static Map<String, MetamodelPersistenceObject> parseToMetamodelPersistenceObject(Map<String, Map<String, Object>> objectMap) {

    }

}

