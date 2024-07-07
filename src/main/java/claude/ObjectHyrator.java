package claude;

import com.mycompany.metamodel.pojo.DomainModel;
import com.mycompany.metamodel.pojo.ObjectDefinition;
import com.mycompany.metamodel.pojo.PropertyDefinition;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ObjectHyrator {

    private DomainModel config;

    public <T> T rehydrate(ResultSet resultSet, String entityName) throws SQLException {
        ObjectDefinition objectDef = config.getObjectDefinition(entityName);
        Class<T> entityClass = (Class<T>) objectDef.getClass() ;

        try {
            T instance = entityClass.getDeclaredConstructor().newInstance();
            for (PropertyDefinition prop : objectDef.getProperties().values()) {
                String columnName = prop.getColumnName();
                Object value = resultSet.getObject(columnName);
                setProperty(instance, prop.getName(), value);
            }
            return instance;
        } catch (Exception e) {
            throw new RuntimeException("Error rehydrating entity: " + entityName, e);
        }
    }
    private void setProperty(Object instance, String propertyName, Object value) {
        // Use reflection or generated setters to set the property
    }
}
