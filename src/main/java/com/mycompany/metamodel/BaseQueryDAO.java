package com.mycompany.metamodel;

import com.mycompany.metamodel.pojo.ObjectDefinition;
import com.mycompany.metamodel.pojo.QueryBuilderResult;
import com.mycompany.metamodel.pojo.QueryDTO;
import com.mycompany.metamodel.query.JDBCQueryRunner;
import com.mycompany.metamodel.query.QueryBuilder;
import com.mycompany.metamodel.query.QueryParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class BaseQueryDAO {

    @Autowired
    private QueryParser queryParser;

    @Autowired
    private QueryBuilder queryBuilder;


    @Autowired
    private JDBCQueryRunner queryRunner;


    public Map getObjectById(String objName, Object id){
        Map<String, QueryDTO> objectById = queryParser.getObjectById(objName, id);
        QueryBuilderResult byId = queryBuilder.getById(objectById);
        return queryRunner.run(byId);

    }

}
