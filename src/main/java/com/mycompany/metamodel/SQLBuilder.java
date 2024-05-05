package com.mycompany.metamodel;

public class SQLBuilder {
    public static String buildInsertQuery(MetamodelPersistenceObject metamodelPersistenceObject) {
        // Construct INSERT query based on metamodelPersistenceObject
        // You may need to customize this based on your specific requirements
        return "INSERT INTO " + metamodelPersistenceObject.getTableName() + " ...";
    }
}
