package com.mycompany.metamodel.pojo;

import lombok.Data;

@Data
public class Relationship {
    private String targetEntity;
    private String cardinality;
    private String mappedBy;

    // Getters and setters
}
