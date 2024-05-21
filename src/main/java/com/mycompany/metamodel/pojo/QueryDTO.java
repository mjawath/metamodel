package com.mycompany.metamodel.pojo;

import com.mycompany.metamodel.pojo.sql.JoinCondition;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class QueryDTO {

    private String name;
    private ObjectDefinition objectDefinition;
    private List<JoinCondition> conditions;

}
