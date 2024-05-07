package com.mycompany.metamodel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class MetamodelLoaderTest {

    @Autowired
    private MetamodelLoader loader;


    @Test
    public void test(){
        loader.load();
        assertNotNull("test");
    }
}