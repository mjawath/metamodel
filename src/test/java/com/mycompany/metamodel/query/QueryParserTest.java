package com.mycompany.metamodel.query;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QueryParserTest {


    private QueryParser queryParser = new QueryParser();


    public void testOneEntityWithAllFields(){
        Map<String ,Object> map = new LinkedHashMap<>();
        map.put("customer","{*}");
        map.put("user","{*}");

    }


}