package com.mycompany.metamodel.query;

import com.mycompany.metamodel.pojo.ObjectDefinition;
import com.mycompany.metamodel.pojo.QueryBuilderResult;
import com.mycompany.metamodel.pojo.QueryDTO;
import com.mycompany.metamodel.pojo.sql.JOOQSql;
import org.jooq.SelectQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class QueryBuilder {

    @Autowired
    private JOOQSql JOOQSql;

    public QueryBuilderResult getById(Map<String, QueryDTO> map) {
        Map<String, QueryBuilderResult.BuilderResult> objectMap = new LinkedHashMap<>();
        QueryBuilderResult queryBuilderResult = new QueryBuilderResult();
        map.entrySet().forEach(entry -> {
            QueryDTO queryDTO = entry.getValue();
            ObjectDefinition objectDefinition = queryDTO.getObjectDefinition();
            QueryBuilderResult.BuilderResult  builderResult= JOOQSql.buildSelect(objectDefinition);
            objectMap.put(entry.getKey(), builderResult);
        });
        queryBuilderResult.setResultByName(objectMap);
        return queryBuilderResult;
    }
}
