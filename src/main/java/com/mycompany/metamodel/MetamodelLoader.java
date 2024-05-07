package com.mycompany.metamodel;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.mycompany.metamodel.pojo.DomainModel;
import com.mycompany.metamodel.pojo.ObjectDefinition;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.Map;

@Component
public class MetamodelLoader {

    public synchronized void load(){
        TypeReference< Map <String,ObjectDefinition>> objects= new TypeReference<Map<String, ObjectDefinition>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        };
        Map<String, ObjectDefinition> definitionMap = ReadFile.readToObject("/DomainManualDefinition.json", "objects", objects);
        DomainModel.getInstance().setObjects(definitionMap);
    }
}
