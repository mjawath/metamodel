package com.mycompany.metamodel.persistence;

import com.mycompany.metamodel.pojo.BuilderResult;
import com.mycompany.metamodel.pojo.ObjectDefinition;
import org.springframework.stereotype.Component;

import java.util.Map;

public interface PersistenceBuilder {

    public BuilderResult build(Map<String, ObjectDefinition> objectDefinitionMap) ;

}
