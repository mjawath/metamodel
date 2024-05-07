package com.mycompany.metamodel.testdomain;

import com.mycompany.metamodel.pojo.BaseEntity;
import lombok.Data;

@Data
public class Customer extends BaseEntity<Long> {
    private String name;
    private String email;
    // Other fields and methods specific to Customer entity
}

// Other persistent entities can similarly extend BaseEntity with appropriate ID types
