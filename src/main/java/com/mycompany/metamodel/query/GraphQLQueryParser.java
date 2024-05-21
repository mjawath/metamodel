package com.mycompany.metamodel.query;

import graphql.language.*;
import graphql.parser.Parser;

import java.util.HashMap;
import java.util.Map;

public class GraphQLQueryParser {
    public static void main(String[] args) {
        String graphqlQuery = "query {  root  { field1 field2    customers { field3 field4 }  }  }";

        // Parse the GraphQL query
        Parser parser = new Parser();
        Document document = parser.parseDocument(graphqlQuery);

        // Initialize the map to store paths and fields
        Map<String, Map<String, String>> pathToFieldsMap = new HashMap<>();

        // Traverse the AST and collect paths and fields
        document.getDefinitions().forEach(definition -> {
            if (definition instanceof OperationDefinition) {
                OperationDefinition operationDefinition = (OperationDefinition) definition;
                operationDefinition.getSelectionSet().getSelections().forEach(selection ->
                        processSelection(selection, "", pathToFieldsMap));
            }
        });

        // Print the resulting map
        pathToFieldsMap.forEach((path, fields) -> System.out.println(path + ": " + fields));
    }

    private static void processSelection(Selection selection, String currentPath, Map<String, Map<String, String>> pathToFieldsMap) {
        if (selection instanceof Field) {
            Field field = (Field) selection;
            String fullPath = currentPath + "." + field.getName();
            Map<String, String> fields = field.getSelectionSet() == null ? new HashMap<>() : getFields(field.getSelectionSet());
            if (field.getSelectionSet() != null) {
                pathToFieldsMap.put(field.getName(), fields);
                field.getSelectionSet().getSelections().forEach(innerSelection ->
                        processSelection(innerSelection, fullPath, pathToFieldsMap));
            }
        }
    }

    private static Map<String, String> getFields(SelectionSet selectionSet) {
        Map<String, String> fields = new HashMap<>();
        selectionSet.getSelections().forEach(selection -> {
            if (selection instanceof Field) {
                fields.put(((Field) selection).getName(), "");
            }
        });
        return fields;
    }
}