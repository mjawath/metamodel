package com.mycompany.metamodel.pojo.sql;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InsertStatementTest {

    @Test
    public void testInsertStatement() {
        InsertStatement insertStatement = new InsertStatement();
        insertStatement.setTableName("customers");
        insertStatement.setInsertColumns(Arrays.asList("id", "name"));
        insertStatement.setInsertValues(Arrays.asList(Arrays.asList(1, "John"), Arrays.asList(2, "Jane")));
        String s = insertStatement.generateSQL();
    }

}