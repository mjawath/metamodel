package claude;

import com.mycompany.metamodel.pojo.DomainModel;
import com.mycompany.metamodel.pojo.ObjectDefinition;
import com.mycompany.metamodel.pojo.PropertyDefinition;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class SQLBuilder {
    private final DomainModel config;

    public SQLBuilder(DomainModel config) {
        this.config = config;
    }

    public String buildInsertSQL(String entityName, Map<String, Object> columnMap) {
        ObjectDefinition objectDef = config.getObjectDefinition(entityName);
        String tableName = objectDef.getTableName();

        List<String> columns = new ArrayList<>(columnMap.keySet());
        String columnString = String.join(", ", columns);
        String valueString = String.join(", ", Collections.nCopies(columns.size(), "?"));

        return String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, columnString, valueString);
    }

    public String buildUpdateSQL(String entityName, Map<String, Object> columnMap, String primaryKeyColumn) {
        ObjectDefinition objectDef = config.getObjectDefinition(entityName);
        String tableName = objectDef.getTableName();

        List<String> setStatements = new ArrayList<>();
        for (String column : columnMap.keySet()) {
            if (!column.equals(primaryKeyColumn)) {
                setStatements.add(column + " = ?");
            }
        }

        String setClause = String.join(", ", setStatements);
        return String.format("UPDATE %s SET %s WHERE %s = ?", tableName, setClause, primaryKeyColumn);
    }

    public String buildSelectSQL(String entityName, SelectOptions options) {
        ObjectDefinition objectDef = config.getObjectDefinition(entityName);
        String tableName = objectDef.getTableName();

        StringBuilder sql = new StringBuilder("SELECT ");

        // Handle columns
        if (options.getColumns().isEmpty()) {
            sql.append("*");
        } else {
            sql.append(String.join(", ", options.getColumns()));
        }

        sql.append(" FROM ").append(tableName);

        // Handle joins
        for (JoinClause joinClause : options.getJoins()) {
            sql.append(" ").append(joinClause.toString());
        }

        // Handle where clauses
        List<String> whereClauses = new ArrayList<>();
        for (Map.Entry<String, Object> entry : options.getCriteria().entrySet()) {
            whereClauses.add(entry.getKey() + " = ?");
        }

        if (!whereClauses.isEmpty()) {
            sql.append(" WHERE ").append(String.join(" AND ", whereClauses));
        }

        return sql.toString();
    }
    public Object convertComplexType(Object value, PropertyDefinition propertyDef) {
        String columnType = propertyDef.getColumnType().toLowerCase();
        switch (columnType) {
            case "array":
                return convertArray(value);
            case "json":
                return convertJSON(value);
            case "datetime":
                return convertDateTime(value);
            case "binary":
                return convertBinary(value);
            case "enum":
                return convertEnum(value);
            case "decimal":
                return convertDecimal(value);
            default:
                return value;
        }
    }

    private String convertArray(Object value) {
//        if (value instanceof List) {
//            return new JSONArray((List<?>) value).toString();
//        } else if (value.getClass().isArray()) {
//            return new JSONArray(value).toString();
//        }
        throw new IllegalArgumentException("Unsupported array type: " + value.getClass());
    }

    private String convertJSON(Object value) {
        if (value instanceof Map) {
//            return new JSONObject((Map<?, ?>) value).toString();
        } else if (value instanceof String) {
            // Assume it's already a JSON string
            return (String) value;
        }
        throw new IllegalArgumentException("Unsupported JSON type: " + value.getClass());
    }

    private String convertDateTime(Object value) {
        if (value instanceof LocalDateTime) {
            return ((LocalDateTime) value).toString();
        } else if (value instanceof LocalDate) {
            return ((LocalDate) value).toString();
        } else if (value instanceof LocalTime) {
            return ((LocalTime) value).toString();
        } else if (value instanceof Instant) {
            return ((Instant) value).toString();
        }
        throw new IllegalArgumentException("Unsupported DateTime type: " + value.getClass());
    }

    private byte[] convertBinary(Object value) {
        if (value instanceof byte[]) {
            return (byte[]) value;
        } else if (value instanceof String) {
            // Assume base64 encoded string
            return Base64.getDecoder().decode((String) value);
        }
        throw new IllegalArgumentException("Unsupported Binary type: " + value.getClass());
    }

    private String convertEnum(Object value) {
        if (value instanceof Enum) {
            return ((Enum<?>) value).name();
        } else if (value instanceof String) {
            return (String) value;
        }
        throw new IllegalArgumentException("Unsupported Enum type: " + value.getClass());
    }

    private BigDecimal convertDecimal(Object value) {
        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        } else if (value instanceof Number) {
            return new BigDecimal(value.toString());
        } else if (value instanceof String) {
            return new BigDecimal((String) value);
        }
        throw new IllegalArgumentException("Unsupported Decimal type: " + value.getClass());
    }
}