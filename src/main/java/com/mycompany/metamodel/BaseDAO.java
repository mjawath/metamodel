package com.mycompany.metamodel;

import com.mycompany.metamodel.persistence.PersistenceBuilder;
import com.mycompany.metamodel.persistence.PersistenceParser;
import com.mycompany.metamodel.persistence.PersistenceRunner;
import com.mycompany.metamodel.pojo.BuilderResult;
import com.mycompany.metamodel.pojo.ObjectDefinition;
import com.mycompany.metamodel.pojo.ParserResult;
import com.mycompany.metamodel.pojo.PersistenceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component

public class BaseDAO {

    @Autowired
    private PersistenceParser parser;
    @Autowired
    private PersistenceBuilder builder;
    @Autowired
    private PersistenceRunner runner;

    public void save(Map map){
        ObjectDefinition customer = parser.parseAnObject("customer", (Map<String, Object>) map.get("customer"));
        BuilderResult builderResult = builder.build(Map.of("customer", customer));
        PersistenceResult runnerResult = runner.insert(builderResult);


    }

    public void saveM(Map<String, Object> map) {
        ParserResult parserResult = parser.parseObjects(map);
        BuilderResult builderResult = builder.build(parserResult.getObjectDefinitionMap());
        PersistenceResult runnerResult = runner.insert(builderResult);


    }

}
