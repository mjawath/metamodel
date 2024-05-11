package com.mycompany.metamodel.pojo.sql;

import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

// Abstract class for SQL statement
abstract class SQLStatement {
    public abstract String generateSQL();
}


// Enum for join types
enum JoinType {
    INNER_JOIN,
    LEFT_JOIN,
    RIGHT_JOIN,
    FULL_JOIN
}

@Data
class JoinCondition {
    private String leftTable;
    private String leftColumn;
    private String rightTable;
    private String rightColumn;

    // Constructor, getters, and setters
}

@Data
class JoinClause {
    private JoinType joinType;
    private String table;
    private List<JoinCondition> conditions;

    // Constructor, getters, and setters
}
// Class for SELECT statement
class SelectStatement extends SQLStatement {
    private Map<String, String> selectColumns;
    private List<String> fromTables;
    private List<String> whereConditions;
    private List<String> groupByColumns;
    private List<String> havingConditions;
    private List<String> orderByColumns;
    private List<JoinClause> joinClauses;

    // Constructor, getters, and setters

    @Override
    public String generateSQL() {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT ");
        if (!selectColumns.isEmpty()) {
            List<String> columnsWithAliases = new ArrayList<>();
            for (Map.Entry<String, String> entry : selectColumns.entrySet()) {
                String column = entry.getKey();
                String alias = entry.getValue();
                columnsWithAliases.add(column + " AS " + alias);
            }
            sqlBuilder.append(String.join(", ", columnsWithAliases));
        } else {
            sqlBuilder.append("*");
        }
        sqlBuilder.append("\nFROM ");
        sqlBuilder.append(String.join(", ", fromTables));
        for (JoinClause joinClause : joinClauses) {
            sqlBuilder.append("\n")
                      .append(joinClause.getJoinType())
                      .append(" ")
                      .append(joinClause.getTable());
            // Append join conditions if any
            // Add other clauses similarly
        }
        return sqlBuilder.toString();
    }
}

// Class for INSERT statement
class InsertStatement extends SQLStatement {
    private String tableName;
    private List<String> insertColumns;
    private List<List<Object>> insertValues;

    // Constructor, getters, and setters

    @Override
    public String generateSQL() {
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
}

@Data
class UpdateStatement extends SQLStatement {
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
        if (!whereConditions.isEmpty()) {
            sqlBuilder.append("\nWHERE ")
                      .append(String.join(" AND ", whereConditions));
        }
        return sqlBuilder.toString();
    }
}

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
