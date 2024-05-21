package com.mycompany.metamodel.pojo.sql;

import lombok.Data;

@Data
public class JoinCondition {
    private String leftTable;
    private String leftColumn;
    private String rightTable;
    private String rightColumn;

    // Constructor, getters, and setters
}
