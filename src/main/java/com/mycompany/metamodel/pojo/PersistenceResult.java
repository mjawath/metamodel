package com.mycompany.metamodel.pojo;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class PersistenceResult {

    private Map<String, Object> resultForTable = new LinkedHashMap<>();
}
