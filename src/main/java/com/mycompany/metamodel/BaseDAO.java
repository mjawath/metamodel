package com.mycompany.metamodel;

import com.mycompany.metamodel.persistence.PersistenceParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component

public class BaseDAO {
    @Autowired
    private JdbcTemplate template;


    @Autowired
    private PersistenceParser parser;

    public void save(Map map){
        template.execute("select * from customer");
        parser.parseObjects("customer", map);
    }

}
