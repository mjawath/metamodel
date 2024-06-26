package com.mycompany.metamodel.pojo;

import lombok.Data;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
}