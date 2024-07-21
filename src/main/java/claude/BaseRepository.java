package claude;

import com.mycompany.metamodel.pojo.DomainModel;
import com.mycompany.metamodel.pojo.ObjectDefinition;
import com.mycompany.metamodel.pojo.PropertyDefinition;
import com.mycompany.metamodel.testdomain.Customer;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Component
public class BaseRepository {

    private DataSource dataSource;
    private PersistentRehydrator rehydrator;
    private SQLBuilder sqlBuilder;
    private Executor executor;
    private DomainModel domainModel;

    public BaseRepository(DataSource dataSource, DomainModel config) {
        this.dataSource = dataSource;
        this.rehydrator = new PersistentRehydrator(config);
        this.sqlBuilder = new SQLBuilder(config);
        this.domainModel = config;
    }

    public <T> T find(String entityName, String id, Class<T> type) {

        // Get the ObjectDefinition from the DomainModel configuration
        ObjectDefinition objectDef = domainModel.getObjectDefinition(entityName);
        if (objectDef == null) {
            throw new IllegalArgumentException("No object definition found for type: " + type.getName());
        }
        // Build the SQL query
        SelectOptions options = rehydrator.getOptionForFindAll(entityName);
        SQLStatement sqlStatement = sqlBuilder.buildSelectSQLx(entityName, options);
        List<? extends T> objects = rehydrator.persistanceToData(executor.executeQueryx(entityName, sqlStatement), entityName);
        return objects.get(0);
    }

    public <T> T findById(String entityName, String id, Class<T> type) {

        // Get the ObjectDefinition from the DomainModel configuration
        ObjectDefinition objectDef = domainModel.getObjectDefinition(entityName);
        if (objectDef == null) {
            throw new IllegalArgumentException("No object definition found for type: " + type.getName());
        }
        // Build the SQL query
        SelectOptions options = rehydrator.getOptionForFindById(entityName,id);
        SQLStatement sqlStatement = sqlBuilder.buildSelectSQLx(entityName, options);
        List<Map<String, Object>> resultMaps = executor.executeQueryx(entityName, sqlStatement);
        List<? extends T> objects = rehydrator.persistanceToData(resultMaps, entityName);
        if(objects == null || objects.isEmpty()) {
            return null;
        }
        return objects.get(0);
    }

    public <T> List<T> findAll(Class<T> type,List<WhereClause> whereClause) {
        String[] split = type.getSimpleName().split("\\.");
        String entityName = split[split.length-1].toLowerCase();
        // Get the ObjectDefinition from the DomainModel configuration
        ObjectDefinition objectDef = domainModel.getObjectDefinition(entityName);
        if (objectDef == null) {
            throw new IllegalArgumentException("No object definition found for type: " + type.getName());
        }
        SelectOptions options = rehydrator.getOptionForFindAll(entityName);
        options.setWhereClauses(whereClause);
        SQLStatement sqlStatement = sqlBuilder.buildSelectSQLx(entityName, options);
        List<T> objects = rehydrator.persistanceToData(executor.executeQueryx(entityName, sqlStatement), entityName);
        return objects;
    }

    public <T> List<T> findAll(Class<T> type) {
        String[] split = type.getSimpleName().split("\\.");
        String entityName = split[split.length-1].toLowerCase();
        // Get the ObjectDefinition from the DomainModel configuration
        ObjectDefinition objectDef = domainModel.getObjectDefinition(entityName);
        if (objectDef == null) {
            throw new IllegalArgumentException("No object definition found for type: " + type.getName());
        }
        SelectOptions options = rehydrator.getOptionForFindAll(entityName);
        SQLStatement sqlStatement = sqlBuilder.buildSelectSQLx(entityName, options);
        List<T> objects = rehydrator.persistanceToData(executor.executeQueryx(entityName, sqlStatement), entityName);
        return objects;
    }

    public <T> T save(T entity) {
        return null;
    }

    public <T> T update(String entityName,T entity) {
      /*  Map<String, Object> columnMap = rehydrator.dataToAttributeMap(entity, entityName);

        SQLStatement sql = sqlBuilder.buildUpdateSQLx(entityName, columnMap);
        ObjectDefinition objectDef = domainModel.getObjectDefinition(entityName);

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
   */
    return null;
    }

    public <T> T delete(T entity) {
        return null;
    }

    public <T> T deleteById(Class<T> type, String id) {
        return null;
    }

    public <T> T deleteAll(Class<T> type) {
        return null;
    }

    public <T> T deleteAll(List<T> entities) {
        return null;
    }

    public <T> T findById(Class<T> type, String id) {
        String[] split = type.getSimpleName().split("\\.");
        String entityName = split[split.length-1].toLowerCase();
        // Get the ObjectDefinition from the DomainModel configuration
        ObjectDefinition objectDef = domainModel.getObjectDefinition(entityName);
        if (objectDef == null) {
            throw new IllegalArgumentException("No object definition found for type: " + type.getName());
        }
        return findById(entityName,id,type);
    }


    public void setExecutor(Executor executor) {
        this.executor = executor;
    }


}
