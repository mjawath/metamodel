package claude;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.metamodel.pojo.DomainModel;
import com.mycompany.metamodel.pojo.ObjectDefinition;
import com.mycompany.metamodel.pojo.PropertyDefinition;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersistentRehydrator {
    private final ObjectMapper objectMapper;
    private final DomainModel config;

    public PersistentRehydrator(DomainModel config) {
        this.objectMapper = new ObjectMapper();
        this.config = config;
    }

    public Map<String, Object> dehydrate(Object jsonCompatibleObject, String entityName) {
        ObjectDefinition objectDef = config.getObjectDefinition(entityName);

        // Convert object to map
        Map<String, Object> objectMap = objectMapper.convertValue(jsonCompatibleObject, Map.class);

        // Create a new map with column names as keys
        Map<String, Object> columnMap = new HashMap<>();

        for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
            PropertyDefinition prop = objectDef.getPropertyDefinition(entry.getKey());
            if (prop != null) {
                columnMap.put(prop.getColumnName(), entry.getValue());
            }
        }

        return columnMap;
    }
    public <T> List<T> rehydrate(List<Map<String, Object>> resultMaps, String entityName) {
        ObjectDefinition objectDef = config.getObjectDefinition(entityName);
        List<T> results = new ArrayList<>();

        for (Map<String, Object> resultMap : resultMaps) {
            T instance = createInstance(objectDef.getClassName());
            for (Map.Entry<String, PropertyDefinition> entry : objectDef.getProperties().entrySet()) {
                String propertyName = entry.getKey();
                PropertyDefinition propDef = entry.getValue();
                Object value = resultMap.get(propDef.getColumnName());
                setProperty(instance, propertyName, value, propDef);
            }
            results.add(instance);
        }

        return results;
    }

    private <T> T createInstance(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            return (T) clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Error creating instance of " + className, e);
        }
    }

    private void setProperty(Object instance, String propertyName, Object value, PropertyDefinition propDef) {
        try {
            Field field = instance.getClass().getDeclaredField(propertyName);
            field.setAccessible(true);
            field.set(instance, convertValue(value, propDef));
        } catch (Exception e) {
            throw new RuntimeException("Error setting property " + propertyName, e);
        }
    }

    private Object convertValue(Object value, PropertyDefinition propDef) {
        // Here you can add logic to convert the value based on the property definition
        // For example, date parsing, enum mapping, etc.
        return value;
    }
}