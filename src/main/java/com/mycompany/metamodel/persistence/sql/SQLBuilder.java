package com.mycompany.metamodel.persistence.sql;

import com.mycompany.metamodel.persistence.PersistenceBuilder;
import com.mycompany.metamodel.pojo.DomainModel;
import com.mycompany.metamodel.pojo.ObjectDefinition;
import com.mycompany.metamodel.pojo.PropertyDefinition;

import java.util.Map;

public class SQLBuilder extends PersistenceBuilder {


    public Object build(Map<String, ObjectDefinition> objectDefinitionMap) {
        DomainModel domainModel = DomainModel.getInstance();
        objectDefinitionMap.entrySet().forEach(entry -> {
            StringBuilder sql = new StringBuilder();
            ObjectDefinition objectDefinition = entry.getValue();
            sql.append("INSERT INTO ").append(objectDefinition.getTableName()).append(" (");

            Map<String, PropertyDefinition> properties = objectDefinition.getProperties();
            // Append column names
            for (String propertyName : properties.keySet()) {
                sql.append(propertyName).append(", ");
            }
            sql.delete(sql.length() - 2, sql.length()); // Remove the last comma and space
            sql.append(") VALUES (");

            // Append named parameters for values
            for (String propertyName : properties.keySet()) {
                sql.append(":").append(propertyName).append(", ");
            }
            sql.delete(sql.length() - 2, sql.length()); // Remove the last comma and space
            sql.append(")");

            sql.toString();

        });
        return domainModel;
      }
}
