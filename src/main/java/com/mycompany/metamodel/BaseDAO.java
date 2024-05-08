package com.mycompany.metamodel;

import com.mycompany.metamodel.persistence.PersistenceBuilder;
import com.mycompany.metamodel.persistence.PersistenceParser;
import com.mycompany.metamodel.pojo.ObjectDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component

public class BaseDAO {

    @Autowired
    private PersistenceParser parser;


    @Autowired
    private PersistenceBuilder builder;

    public void save(Map map){
        ObjectDefinition customer = parser.parseAnObject("customer", map);
        Object builderResult = builder.build(Map.of("customer", customer));
    }

}
