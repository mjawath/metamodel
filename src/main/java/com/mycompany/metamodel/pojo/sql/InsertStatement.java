package com.mycompany.metamodel.pojo.sql;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
// Class for INSERT statement
public class InsertStatement extends SQLStatement {
    private String tableName;
    private List<String> insertColumns;
    private List<Map<String, Object>> insertValues;
    private ParameterType parameterType = ParameterType.INDEXED;

    public static enum ParameterType {
        INDEXED,
        NAMED
    }

    public String generateSingleTableSQL() {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("INSERT INTO ")
                .append(tableName)
                .append(" (")
                .append(String.join(", ", insertColumns))
                .append(")\nVALUES\n");


        List<String> valuePlaceholders = new ArrayList<>();
        for (int i = 0; i < insertValues.size(); i++) {
            List<String> rowPlaceholders = new ArrayList<>();
            for (String column : insertColumns) {
                if (parameterType == ParameterType.NAMED) {
                    rowPlaceholders.add(":" + column + "_" + (i + 1));
                } else {
                    rowPlaceholders.add("?");
                }
            }
            valuePlaceholders.add("(" + String.join(" , ", rowPlaceholders) + ")");
        }

        sqlBuilder.append(String.join("\n, ", valuePlaceholders));

       /* for (int x = 0; x < insertValues.size(); x++) {
            sqlBuilder.append(x > 0 ? ",\n" : "\n");

            Map<String, Object> row = insertValues.get(x);
            sqlBuilder.append("(");
            for (int y = 0; y < insertColumns.size(); y++) {
                if (y > 0) {
                    sqlBuilder.append(", ");
                }
                sqlBuilder.append(row.get(insertColumns.get(y)));
                sqlBuilder.append(row.get(insertColumns.get(y)));
                if (parameterType == ParameterType.NAMED) {

                } else {
                    sqlBuilder.append(y + 1);
                    sqlBuilder.append("?");
                }
            }
            sqlBuilder.append(")");
        }*/
        return sqlBuilder.toString();
    }

    @Override
    public String generateSQL() {
        return generateSingleTableSQL();
    }
}
