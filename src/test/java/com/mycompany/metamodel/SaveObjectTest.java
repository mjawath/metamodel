package com.mycompany.metamodel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
public class SaveObjectTest {

    @Autowired
    private BaseDAO baseDAO;

    @Autowired
    private MetamodelLoader loader;

    @Test
    public void test(){
        baseDAO.save(null);
        Assertions.assertNotNull("test");
    }
}
