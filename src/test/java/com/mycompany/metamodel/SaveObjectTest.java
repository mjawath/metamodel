package com.mycompany.metamodel;

import com.mycompany.metamodel.testdomain.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class SaveObjectTest {

    @Autowired
    private BaseDAO baseDAO;

    @Autowired
    private MetamodelLoader loader;

    @Test
    public void test(){
        Map customer = ReadFile.readToAnMap("/Customers.json");
        baseDAO.save(customer);
        Assertions.assertNotNull("test");
    }


    @Test
    public void testmulti(){
        Map customer = ReadFile.readToAnMap("/Multi.json");
        baseDAO.saveM(customer);
        Assertions.assertNotNull("test");
    }
}
