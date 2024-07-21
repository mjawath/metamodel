package claude;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.metamodel.pojo.DomainModel;
import com.mycompany.metamodel.pojo.ObjectDefinition;
import com.mycompany.metamodel.pojo.PropertyDefinition;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class PersistentRehydrator {
    private final ObjectMapper objectMapper;
    private final DomainModel domainModel;

    public PersistentRehydrator(DomainModel domainModel) {
        this.objectMapper = new ObjectMapper();
        this.domainModel = domainModel;
    }

    public SelectOptions getOptionForFindAll(String entityName) {
        List<String> columnnames = domainModel.getObjectDefinition(entityName)
                .getProperties().values().stream()
                .map(PropertyDefinition::getColumnName).toList();
        return SelectOptions.builder().columns(columnnames).build();
    }

    public SelectOptions getOptionForFindAll(String entityName, String orderByColumn) {
        return null;
    }

    public SelectOptions getOptionForFindById(String entityName, Object idValue) {
        ObjectDefinition objectDefinition = domainModel.getObjectDefinition(entityName);
        PropertyDefinition primaryKey = objectDefinition.getPrimaryKey();
        Map params = new LinkedHashMap();
        params.put("id",idValue);

        return SelectOptions.builder()
                .columns(objectDefinition.getAllOwnProps())//
                .criteria(Map.of(primaryKey.getName(), "= :id"))
                .params(params)
                .build();
    }

    public Map<String, Object> dataToAttributeMap(Object jsonCompatibleObject, String entityName) {
        ObjectDefinition objectDef = domainModel.getObjectDefinition(entityName);

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

    public <T> List<T> persistanceToData(List<Map<String, Object>> resultMaps, String entityName) {
        return persistanceToData(resultMaps, entityName, null);
    }

    public <T> List<T> persistanceToData(List<Map<String, Object>> resultMaps, String entityName, Class<T> projectionClass) {
        ObjectDefinition objectDef = domainModel.getObjectDefinition(entityName);
        List<T> results = new ArrayList<>();

        boolean useObjectMapper = shouldUseObjectMapper(objectDef);

        String className = objectDef.getClassName();
        try {
            Class<T> clazz = className == null ? projectionClass : (Class<T>) Class.forName(className);

            for (Map<String, Object> resultMap : resultMaps) {//we might have many domain objects like invoice ,within that customer
                Map<String, Object> convertedMap = convertMapToPropertyNames(resultMap, objectDef);
                T instance = useObjectMapper ?
                        createInstanceWithObjectMapper(convertedMap, clazz) :
                        createInstanceWithReflection(convertedMap,objectDef, clazz);
                results.add(instance);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class not found: " + className, e);
        }

        return results;
    }

    private boolean shouldUseObjectMapper(ObjectDefinition objectDef) {
        // Decide based on object complexity, e.g., number of fields
//        return objectDef.getProperties().size() > 10; // arbitrary threshold
        return true;
    }

    private <T> T createInstanceWithObjectMapper(Map<String, Object> convertedMap, Class<T> clazz) {
        return objectMapper.convertValue(convertedMap, clazz);
    }

    private <T> T createInstanceWithReflection(Map<String, Object> convertedMap, ObjectDefinition objectDefinition,
                                               Class<T> clazz) {
        try {

            T instance = clazz.getDeclaredConstructor().newInstance();
            for (Map.Entry<String, Object> entry : convertedMap.entrySet()) {
                PropertyDefinition propDef = objectDefinition.getProperties().get(entry.getKey());
                setProperty(instance,entry.getKey(),entry.getValue(),propDef);
            }
            return instance;
        } catch (Exception e) {
            throw new RuntimeException("Error creating instance with reflection", e);
        }
    }

    private Map<String, Object> convertMapToPropertyNames(Map<String, Object> resultMap, ObjectDefinition objectDef) {
        Map<String, Object> convertedMap = new HashMap<>();
        for (Map.Entry<String, PropertyDefinition> entry : objectDef.getProperties().entrySet()) {
            String propertyName = entry.getKey();
            String columnName = entry.getValue().getColumnName();
            if (resultMap.containsKey(columnName)) {
                convertedMap.put(propertyName, convertValue(resultMap.get(columnName), entry.getValue()));
            }
        }
        return convertedMap;
    }


    private Object convertValue(Object value, PropertyDefinition propDef) {
        // Here you can add logic to convert the value based on the property definition
        // For example, date parsing, enum mapping, etc.
        return value;
    }
    private void setProperty(Object instance, String propertyName, Object value, PropertyDefinition propDef) {
        try {
            Field field = instance.getClass().getDeclaredField(propertyName);
            field.setAccessible(true);
            field.set(instance, convertValue(value, propDef));
        } catch (Exception e) {
//            throw new RuntimeException("Error setting property " + propertyName, e);
            // TODO: Log error properly
        }
    }
}