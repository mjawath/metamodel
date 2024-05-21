package com.mycompany.metamodel.pojo.sql;

import java.util.List;

// Class for DELETE statement
class DeleteStatement extends SQLStatement {
    private String tableName;
    private List<String> deleteConditions;

    // Constructor, getters, and setters

    @Override
    public String generateSQL() {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("DELETE FROM ")
                .append(tableName);
        if (!deleteConditions.isEmpty()) {
            sqlBuilder.append("\nWHERE ")
                    .append(String.join(" AND ", deleteConditions));
        }
        return sqlBuilder.toString();
    }
}
