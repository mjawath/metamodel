package com.mycompany.metamodel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class BaseQueryDAOTest {

    @Autowired
    private BaseQueryDAO baseQueryDAO;


    @Test
    public void queryAll(){
        baseQueryDAO.getObjectById("customer", 1);
    }

}