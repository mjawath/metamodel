package com.mycompany.metamodel;

import claude.BaseRepoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(BaseRepoConfiguration.class)
public class MetamodelApplication {

	public static void main(String[] args) {
		SpringApplication.run(MetamodelApplication.class, args);
	}

}
