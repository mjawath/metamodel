package com.mycompany.metamodel.pojo;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;
@Data
public class BuilderResult {
    private Map<String, Object> sqlMap=new LinkedHashMap<>();
}
