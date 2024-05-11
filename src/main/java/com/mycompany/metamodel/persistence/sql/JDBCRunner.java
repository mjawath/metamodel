package com.mycompany.metamodel.persistence.sql;

import com.mycompany.metamodel.persistence.PersistenceRunner;
import com.mycompany.metamodel.pojo.BuilderResult;
import com.mycompany.metamodel.pojo.PersistenceResult;
import org.jooq.DSLContext;
import org.jooq.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JDBCRunner implements PersistenceRunner {

    private NamedParameterJdbcTemplate template;
    @Autowired
    private DSLContext context;


    public PersistenceResult insert(BuilderResult object) {
        PersistenceResult result = new PersistenceResult();
        if (object != null && object.getSqlMap() != null) {
            for (Map.Entry<String, Object> entry : object.getSqlMap().entrySet()) {
                if (entry.getValue() instanceof Insert) {
                    int count = context.execute((Insert) entry.getValue());
                    result.getResultForTable().put("count", count);
                }
            }
        }
//        template.execute("select * from customer");

        // Implement save logic using NamedParameterJdbcTemplate
        // You would generate the insert SQL statement using a SqlBuilder or directly
        // and then execute it with NamedParameterJdbcTemplate
//        jdbcTemplate.execute(object)
        return result;
    }

    public void update(Object object) {
        // Implement update logic using NamedParameterJdbcTemplate
        // You would generate the update SQL statement using a SqlBuilder or directly
        // and then execute it with NamedParameterJdbcTemplate
    }

    public void delete(Object object) {
        // Implement delete0 logic using NamedParameterJdbcTemplate
        // You would generate the delete SQL statement using a SqlBuilder or directly
        // and then execute it with NamedParameterJdbcTemplate
    }

    public Object findById(Class<?> clazz, Object id) {
        // Implement find by id logic using NamedParameterJdbcTemplate
        // You would generate the select SQL statement using a SqlBuilder or directly
        // and then execute it with NamedParameterJdbcTemplate
        return null; // Placeholder, replace with actual implementation
    }

}
