package com.mycompany.metamodel.query;

import com.mycompany.metamodel.pojo.DomainModel;
import com.mycompany.metamodel.pojo.ObjectDefinition;
import com.mycompany.metamodel.pojo.QueryDTO;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.PlainSelect;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class QueryParser {

    @Autowired
    private DSLContext context;

    public Map<String, QueryDTO> getObjectById(String objName, Object id) {
        DomainModel domainModel = DomainModel.getInstance();
        ObjectDefinition objectDefinition = domainModel.getObjectDefinition(objName);
        if (objectDefinition == null)
            return null;
        Map<String, QueryDTO> map = new LinkedHashMap<>();
        QueryDTO queryDTO = new QueryDTO();
        queryDTO.setName(objName);
        ObjectDefinition definitioncloned = objectDefinition.clone();
        queryDTO.setObjectDefinition(definitioncloned);
        map.put(objName, queryDTO);
        return map;
    }

    public Map getObjectByMapQuery(Map<String, Object> map) {
        DomainModel domainModel = DomainModel.getInstance();
        String qry = "customer";
        map.put("customer", "{*}");
        map.put("user", "{*}");

        map.entrySet().forEach(entry -> {
            ObjectDefinition objectDefinition = domainModel.getObjectDefinition(qry);
//            objectDefinition.getPropertyDefinition()
        });
        return null;
    }


    public Map getObject(String sqlStr) {
        try {
            PlainSelect select = (PlainSelect) CCJSqlParserUtil.parse(sqlStr);
            select.getSelectItems();
        } catch (JSQLParserException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
