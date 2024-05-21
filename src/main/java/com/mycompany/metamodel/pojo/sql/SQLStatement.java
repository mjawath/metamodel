package com.mycompany.metamodel.pojo.sql;

import lombok.Data;

import java.util.*;

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

