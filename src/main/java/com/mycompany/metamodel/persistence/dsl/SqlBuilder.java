package com.mycompany.metamodel.persistence.dsl;

import org.jooq.*;
import org.jooq.impl.*;

public class SqlBuilder {

    public String build(Select<?> select) {
        return select.toString();
    }
}
