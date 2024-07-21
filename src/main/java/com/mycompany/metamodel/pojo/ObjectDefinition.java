package com.mycompany.metamodel.pojo;

import claude.RelationshipDefinition;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Data
public class ObjectDefinition extends Node{
    private String title;
    private String entityName;
    private String description;
    private String type;
    private String className;
    private String tableName;
    private String operation;
    private Map<String, PropertyDefinition> properties;
    private Map<String, RelationshipDefinition> relationships;

    private String[] required;
    private List<Map<String,Object>> valueBag = new LinkedList<>();

    private Object value;

    //check and return PropertyDefinition   from the map

    public PropertyDefinition getPropertyDefinition(String propertyName){
        return properties.get(propertyName);
    }

    //clone this object
    public ObjectDefinition clone(){
        ObjectDefinition clone = new ObjectDefinition();
        clone.setTitle(this.title);
        clone.setEntityName(this.entityName);
        clone.setDescription(this.description);
        clone.setType(this.type);
        clone.setClassName(this.className);
        clone.setTableName(this.tableName);
        clone.setOperation(this.operation);
        clone.setProperties(this.properties);
        clone.setRequired(this.required);
        clone.setValue(this.value);
        if(this.properties != null) {
            clone.properties = new LinkedHashMap<>();
            for(Map.Entry<String, PropertyDefinition> entry :  this.properties.entrySet()){
                clone.properties.put(entry.getKey(), entry.getValue().clone());
            }
        }
        return clone;
    }

    //get primary key
    public PropertyDefinition getPrimaryKey() {
        return getProperties().values().stream()
                .filter(PropertyDefinition::isPrimaryKey)
                .findFirst()
//                .map(PropertyDefinition::getName)
//                .orElseThrow(() -> new IllegalStateException("No primary key defined for type: " + this.getEntityName()));
                .orElse(null);
    }

    public List<String> getAllOwnProps() {
        return getProperties().values().stream()
                .filter(PropertyDefinition::isAttribute)
                .map(PropertyDefinition::getName).toList();
    }

    public List<String> getColumns(List<String> attributes) {
        if(attributes==null) return null;
        return attributes.stream().map(attribute -> {
            PropertyDefinition propertyDefinition = getProperties().get(attribute);
            if(propertyDefinition==null || !propertyDefinition.isAttribute()){
                return null;
            }
            return propertyDefinition.getColumnName();
        }).toList();
    }
}