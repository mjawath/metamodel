package com.mycompany.metamodel.pojo.sql;

import com.mycompany.metamodel.pojo.ObjectDefinition;
import com.mycompany.metamodel.pojo.QueryBuilderResult;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Component
public class JOOQSql {

    @Autowired
    private DSLContext dslContext;

    public String select() {
//        Query query = dslContext.select(field("BOOK.TITLE"), field("AUTHOR.FIRST_NAME"), field("AUTHOR.LAST_NAME"))
//                .from(table("BOOK"))
//                .join(table("AUTHOR"))
//                .on(field("BOOK.AUTHOR_ID").eq(field("AUTHOR.ID")))
//                .where(field("BOOK.PUBLISHED_IN").eq(1948));
//        String sql = query.getSQL();
//        List<Object> bindValues = query.getBindValues();
        return null;
    }


//    public SqlBuilder(DataSource dataSource) {
//        this.dslContext = DSL.using(dataSource, SQLDialect.MYSQL); // Assuming MySQL
//    }

    public  QueryBuilderResult.BuilderResult buildSelect(ObjectDefinition objectDefinition) {
        Map<String, Object> objectMap = new LinkedHashMap<>();
        QueryBuilderResult.BuilderResult qbr =new QueryBuilderResult.BuilderResult();
        qbr.setEntityName(objectDefinition.getEntityName());
        Map<String, String> attributeByColumnName = new LinkedHashMap<>();
        List<SelectField> selectFields = new ArrayList<>();
        objectDefinition.getProperties().forEach((propName, value) -> {
            if(value.isAttribute()){
                selectFields.add(field(value.getColumnName()));
                attributeByColumnName.put(value.getColumnName(),propName );
            }
        });

        qbr.setAttributeByColumnName(attributeByColumnName);
        qbr.setPropertyDefinition(objectDefinition.getProperties());
        qbr.setQueryObject(dslContext.select(selectFields)
                .from(table(objectDefinition.getTableName()))
                .getQuery());
        return qbr;
    }

    public Object buildInsert(ObjectDefinition objectDefinition) {

        InsertSetStep insertSetStep = dslContext.insertInto(table(objectDefinition.getTableName()));
        List<Field> fieldList = new ArrayList<>();
        List values = new ArrayList<>();
        objectDefinition.getProperties().forEach((columnName, value) -> {
            fieldList.add(field(value.getColumnName()));
            values.add(value.getValue());
        });
        InsertValuesStepN values1 = insertSetStep.columns(fieldList).values(values);
        return values1;

//        InsertQuery insertQuery = dslContext.insertQuery(table(tableName));
//        Field id = field("id");
//        insertQuery.addValue(id, 1);
//        values.forEach((columnName, value) -> {
//            Field field = field(columnName);
//            insertQuery.addValue(field, value);
//        });
//        return insertSetStep.toString();
    }

//    public UpdateQuery<?> buildUpdate(String tableName, Map<String, Object> values, Condition condition) {
//        UpdateQuery<?> updateQuery = dslContext.updateQuery(table(tableName));
//        values.forEach((columnName, value) -> updateQuery.addValue(field(columnName), value));
//        updateQuery.addConditions(condition);
//        return updateQuery;
//    }

//    public DeleteQuery buildDelete(String tableName, Condition condition) {
//        return dslContext.deleteQuery(table(tableName)).addConditions(condition);
//    }

    private Table<?> table(String tableName) {
        return DSL.table(tableName);
    }

    private Field<?> field(String columnName) {
        return DSL.field(columnName);
    }

}
