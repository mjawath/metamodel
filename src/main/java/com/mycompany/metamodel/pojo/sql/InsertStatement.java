package com.mycompany.metamodel.pojo.sql;

import com.mycompany.metamodel.prop.Configurations;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
// Class for INSERT statement
public class InsertStatement extends SQLStatement {
    private String tableName;
    private List<String> insertColumns;
    private List<List<Object>> insertValues;
    private Configurations properties;

    // Constructor, getters, and setters


    public String generateSingleTableSQL() {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("INSERT INTO ")
                .append(tableName)
                .append(" (")
                .append(String.join(", ", insertColumns))
                .append(")\nVALUES ");
        for (List<Object> row : insertValues) {
            sqlBuilder.append("(")
                    .append(String.join(", ", row.stream().map(Object::toString).collect(Collectors.toList())))
                    .append("), ");
        }
        sqlBuilder.setLength(sqlBuilder.length() - 2); // Remove the last comma and space
        return sqlBuilder.toString();
    }

    @Override
    public String generateSQL() {
        return generateSingleTableSQL();
    }
}
