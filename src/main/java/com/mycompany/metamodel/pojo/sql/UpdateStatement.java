package com.mycompany.metamodel.pojo.sql;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class UpdateStatement extends SQLStatement {
    private String tableName;
    private Map<String, Object> setValues;
    private List<String> whereConditions;


    // Constructor, getters, and setters

    @Override
    public String generateSQL() {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("UPDATE ")
                .append(tableName)
                .append("\nSET ");
        List<String> setClauses = new ArrayList<>();
        for (Map.Entry<String, Object> entry : setValues.entrySet()) {
            setClauses.add(entry.getKey() + " = " + entry.getValue());
        }
        sqlBuilder.append(String.join(", ", setClauses));
        if (whereConditions!=null && !whereConditions.isEmpty()) {
            sqlBuilder.append("\nWHERE ")
                    .append(String.join(" AND ", whereConditions));
        }
        return sqlBuilder.toString();
    }
}
