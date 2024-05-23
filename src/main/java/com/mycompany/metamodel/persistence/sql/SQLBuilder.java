package com.mycompany.metamodel.persistence.sql;

import com.mycompany.metamodel.persistence.PersistenceBuilder;
import com.mycompany.metamodel.pojo.BuilderResult;
import com.mycompany.metamodel.pojo.DomainModel;
import com.mycompany.metamodel.pojo.ObjectDefinition;
import com.mycompany.metamodel.pojo.PropertyDefinition;
import com.mycompany.metamodel.pojo.sql.InsertStatement;
import com.mycompany.metamodel.pojo.sql.JOOQSql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SQLBuilder implements PersistenceBuilder {

    public BuilderResult build(Map<String, ObjectDefinition> objectDefinitionMap) {
        DomainModel domainModel = DomainModel.getInstance();
        BuilderResult builderResult = new BuilderResult();
        builderResult.setSqlMap(new LinkedHashMap<>());
        domainModel.setObjects(objectDefinitionMap);
        objectDefinitionMap.entrySet().forEach(entry -> {
            ObjectDefinition objectDefinition = entry.getValue();

            InsertStatement insert =new InsertStatement();
            insert.setTableName(objectDefinition.getTableName());
            List<String> columns = new ArrayList<>();
            List<Map<String, Object>> values = new ArrayList<>();

            for (Map.Entry<String, PropertyDefinition> propertyEntry : objectDefinition.getProperties().entrySet()) {
                PropertyDefinition property = propertyEntry.getValue();
                columns.add(property.getColumnName());
                Map<String, Object> row = new HashMap<>();
                row.put(property.getColumnName(), property.getValue());
                values.add(row);


            }
            insert.setInsertColumns(columns);
//            sql.setInsertValues(values);
            String sqlQuery = insert.generateSQL();

            builderResult.getSqlMap().put(entry.getKey(), insert);
        });
        return builderResult;
    }

        public BuilderResult build2(Map<String, ObjectDefinition> objectDefinitionMap) {
        DomainModel domainModel = DomainModel.getInstance();
        BuilderResult builderResult = new BuilderResult();
        builderResult.setSqlMap(new LinkedHashMap<>());
        domainModel.setObjects(objectDefinitionMap);
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

            builderResult.getSqlMap().put(entry.getKey(), sql.toString());

        });
        return builderResult;
      }
}
