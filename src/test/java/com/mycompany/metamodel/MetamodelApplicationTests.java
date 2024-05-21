package com.mycompany.metamodel;

import com.fasterxml.jackson.databind.JsonNode;
import com.networknt.schema.JsonSchema;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static com.mycompany.metamodel.ReadFile.*;

@SpringBootTest
class MetamodelApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void getValidJson() throws IOException {
		JsonNode json = readJsonFile("/Order.json");
		Assertions.assertNotNull(json);
	}

	@Test
	public void getOrderSchema() throws IOException {
		JsonSchema jsonSchema = readToSchema("/Order.json");
		Assertions.assertNotNull(jsonSchema.getRefSchemaNode(""));
	}

	@Test
	public void getOrderFromSchemas() throws IOException {
		JsonSchema jsonSchema = readToSchema("/Order.json");
		Assertions.assertNotNull(jsonSchema.getRefSchemaNode(""));
	}

	@Test
	public void getMeta() throws IOException {
		JsonNode jsonNode = readJsonFile("/DomainManualDefinition.json");
		Assertions.assertNotNull(jsonNode.get("objects").get("customer").get("properties").get("name").get("required"));
	}





}
