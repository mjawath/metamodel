package com.mycompany.metamodel.query;

import com.mycompany.metamodel.pojo.QueryBuilderResult;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class JDBCQueryRunner {

    @Autowired
    private DSLContext context;

    public Map run( QueryBuilderResult input) {
        Map output = new LinkedHashMap();
        input.getResultByName().entrySet().forEach(entry -> {
            QueryBuilderResult.BuilderResult entityLvl = entry.getValue();
            Result<Record> fetch = ((SelectQuery)entityLvl.getQueryObject()).fetch();


                for (org.jooq.Record record : fetch) {
                    Map<String, Object> row = new LinkedHashMap<>();
                    for (Field field : record.fields()) {
                            Object value = record.getValue(field);
                            row.put(field.getName(), value);
                        }
                    }
        });

        return null;
    }
}
