package com.mycompany.metamodel;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class ReadFile {



    //read file from resource folder
    public static String readFileFromResource(String fileName)  {


        Path path = new File(fileName).toPath();
        try {
            return Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    read json file and return jsonnode

    public static JsonNode readJsonFile(String filePath)  {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            InputStream inputStream = objectMapper.getClass().getResourceAsStream(filePath);
            return objectMapper.readTree(inputStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static JsonSchema readToSchema(String filePath) {

        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
        JsonSchema jsonSchema = factory.getSchema(readJsonFile(filePath));
        return jsonSchema;
    }

    public static JsonNode readJsonFromString(String input)  {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readTree(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T readToObject(String input,String objectPath, TypeReference<T> trf)  {
        JsonNode jsonNode = readJsonFile(input);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        try {
            return objectMapper.readValue(jsonNode.get(objectPath).toString(),trf);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T readToAnObject(String input,String objectPath, Class<T> trf)  {
        JsonNode jsonNode = readJsonFile(input);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        try {
            return objectMapper.readValue(jsonNode.toString(),trf);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static Map readToAnMap(String input)  {
        JsonNode jsonNode = readJsonFile(input);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        try {
            return objectMapper.readValue(jsonNode.toString(), Map.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
