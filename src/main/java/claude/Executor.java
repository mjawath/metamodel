package claude;

import com.mycompany.metamodel.pojo.DomainModel;
import com.mycompany.metamodel.pojo.ObjectDefinition;
import com.mycompany.metamodel.pojo.PropertyDefinition;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Executor {
    private final DataSource dataSource;
    private final PersistentRehydrator rehydrator;
    private final SQLBuilder sqlBuilder;
    private final DomainModel config;

    public Executor(DataSource dataSource, DomainModel config) {
        this.dataSource = dataSource;
        this.rehydrator = new PersistentRehydrator(config);
        this.sqlBuilder = new SQLBuilder(config);
        this.config = config;
    }

    public void persist(Object entity, String entityName) {
        Map<String, Object> columnMap = rehydrator.dataToPersistance(entity, entityName);
        String sql = sqlBuilder.buildInsertSQL(entityName, columnMap);
        ObjectDefinition objectDef = config.getObjectDefinition(entityName);

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int paramIndex = 1;
            for (Map.Entry<String, Object> entry : columnMap.entrySet()) {
                PropertyDefinition propDef = objectDef.getPropertyDefinition(entry.getKey());
                Object value = sqlBuilder.convertComplexType(entry.getValue(), propDef);
                setParameter(stmt, paramIndex++, value, propDef.getColumnType());
            }

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error persisting entity", e);
        }
    }

    public int update(Object entity, String entityName, String primaryKeyColumn) {
        Map<String, Object> columnMap = rehydrator.dataToPersistance(entity, entityName);
        String sql = sqlBuilder.buildUpdateSQL(entityName, columnMap, primaryKeyColumn);
        ObjectDefinition objectDef = config.getObjectDefinition(entityName);

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int paramIndex = 1;
            for (Map.Entry<String, Object> entry : columnMap.entrySet()) {
                if (!entry.getKey().equals(primaryKeyColumn)) {
                    PropertyDefinition propDef = objectDef.getPropertyDefinition(entry.getKey());
                    Object value = sqlBuilder.convertComplexType(entry.getValue(), propDef);
                    setParameter(stmt, paramIndex++, value, propDef.getColumnType());
                }
            }

            // Set primary key
            PropertyDefinition pkPropDef = objectDef.getPropertyDefinition(primaryKeyColumn);
            Object pkValue = sqlBuilder.convertComplexType(columnMap.get(primaryKeyColumn), pkPropDef);
            setParameter(stmt, paramIndex, pkValue, pkPropDef.getColumnType());

            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating entity", e);
        }
    }

    private void setParameter(PreparedStatement stmt, int index, Object value, String columnType) throws SQLException {
        if (value == null) {
            stmt.setNull(index, Types.NULL);
        } else {
            switch (columnType.toLowerCase()) {
                case "array":
                    stmt.setString(index, (String) value);
                    break;
                case "json":
                    stmt.setString(index, (String) value);
                    break;
                case "datetime":
                    stmt.setString(index, (String) value);
                    break;
                case "binary":
                    stmt.setBytes(index, (byte[]) value);
                    break;
                case "enum":
                    stmt.setString(index, (String) value);
                    break;
                case "decimal":
                    stmt.setBigDecimal(index, (BigDecimal) value);
                    break;
                default:
                    stmt.setObject(index, value);
            }
        }
    }


    public List<Map<String, Object>> executeQuery(String entityName, SelectOptions options) {
        String sql = sqlBuilder.buildSelectSQL(entityName, options);
        List<Object> parameters = new ArrayList<>(options.getCriteria().values());

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < parameters.size(); i++) {
                stmt.setObject(i + 1, parameters.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                List<Map<String, Object>> results = new ArrayList<>();
                while (rs.next()) {
                    Map<String, Object> row = resultSetToMap(rs);
                    results.add(rehydrate(row, entityName));                }
                return results;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error executing query", e);
        }
    }

   private Map<String, Object> resultSetToMap(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        Map<String, Object> row = new HashMap<>();

        for (int i = 1; i <= columnCount; i++) {
            String columnName = metaData.getColumnName(i);
            Object value = rs.getObject(i);
            row.put(columnName, value);
        }

        return row;
    }


    public Map<String, Object> rehydrate(Map<String, Object> data, String entityName) {
        ObjectDefinition objectDef = config.getObjectDefinition(entityName);
        Map<String, Object> result = new HashMap<>();

        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String columnName = entry.getKey();
            Object value = entry.getValue();

            // Map column name to property name
            String propertyName = mapColumnToProperty(objectDef, columnName);
            if (propertyName != null) {
                result.put(propertyName, transformValue(value, propertyName, objectDef));
            }
        }

        return result;
    }

    private String mapColumnToProperty(ObjectDefinition objectDef, String columnName) {
        for (Map.Entry<String, PropertyDefinition> entry : objectDef.getProperties().entrySet()) {
            if (entry.getValue().getColumnName().equalsIgnoreCase(columnName)) {
                return entry.getKey();
            }
        }
        return null; // Column doesn't map to any property
    }

    private Object transformValue(Object value, String propertyName, ObjectDefinition objectDef) {
        PropertyDefinition propDef = objectDef.getProperties().get(propertyName);
        // Here you can add logic to transform the value based on the property definition
        // For example, date formatting, enum mapping, etc.
        return value;
    }
}