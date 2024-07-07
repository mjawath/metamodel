package com.mycompany.metamodel.persistence;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

import static com.mycompany.utils.JsonRead.readJsonFromResource;

class PersistenceParserTest {

    private final PersistenceParser persistenceParser = new PersistenceParser();

    @Test
    void parse() {

        Map map = readJsonFromResource("testpayload/parser/Customer.json", Map.class);
        Map parse = persistenceParser.parse(map);
    }


}