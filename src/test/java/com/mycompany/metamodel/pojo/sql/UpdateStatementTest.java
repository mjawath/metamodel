package com.mycompany.metamodel.pojo.sql;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UpdateStatementTest {

    @Test
    void generateSQL() {
        UpdateStatement us= new UpdateStatement();
        us.setTableName("customers");
        us.setSetValues(Map.of("id", 1, "name", "John"));
        us.setWhereConditions(Arrays.asList("id = 1"));
        String update = us.generateSQL();
        assertEquals("UPDATE customers\nSET name = John, id = 1", update);

    }
}