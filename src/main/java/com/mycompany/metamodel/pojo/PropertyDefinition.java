package com.mycompany.metamodel.pojo;

import lombok.Data;

@Data
public class PropertyDefinition extends Node {
    private String type;
    private String name;
    private String columnName;
    private String columnType;
    private boolean primaryKey;
    private boolean required;
    private boolean autoIncrement;
    private int columnSize;

    private Object value;
    // Getters and setters for all properties
    // Implement as needed

    //override clone
    @Override
    public PropertyDefinition clone() {

        //clone each field
        PropertyDefinition clone = new PropertyDefinition();
        clone.setType(this.type);
        clone.setName(this.name);
        clone.setColumnName(this.columnName);
        clone.setColumnType(this.columnType);
        clone.setPrimaryKey(this.primaryKey);
        clone.setRequired(this.required);
        clone.setAutoIncrement(this.autoIncrement);
        clone.setColumnSize(this.columnSize);
        clone.setValue(this.value);

        return clone;

    }
}