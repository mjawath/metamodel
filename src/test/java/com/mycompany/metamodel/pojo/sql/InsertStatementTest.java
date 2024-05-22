package com.mycompany.metamodel.pojo.sql;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InsertStatementTest {

    @Test
    public void testInsertStatement() {
        InsertStatement insertStatement = new InsertStatement();
        insertStatement.setTableName("customers");
        insertStatement.setInsertColumns(Arrays.asList("id", "name"));
        insertStatement.setInsertValues(Arrays.asList(Map.of("id", 1, "name", "John")
                , Map.of("id", 2, "name", "Doe")
                , Map.of("id", 3, "name", "cool"))
        );
        String s = insertStatement.generateSQL();
        assertEquals("INSERT INTO customers (id, name)\n" +
                "VALUES\n" +
                "(? , ?)\n" +
                ", (? , ?)\n" +
                ", (? , ?)", s);
    }

    @Test
    public void testInsertStatementNamed() {
        InsertStatement insertStatement = new InsertStatement();
        insertStatement.setParameterType(InsertStatement.ParameterType.NAMED);
        insertStatement.setTableName("customers");
        insertStatement.setInsertColumns(Arrays.asList("id", "name"));
        insertStatement.setInsertValues(Arrays.asList(Map.of("id", 1, "name", "John"),
                Map.of("id", 2, "name", "Doe")));
        String s = insertStatement.generateSQL();
        assertEquals("INSERT INTO customers (id, name)\n" +
                "VALUES\n" +
                "(:id_1 , :name_1)\n" +
                ", (:id_2 , :name_2)", s);
    }

}