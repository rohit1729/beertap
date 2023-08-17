package com.codesherpa.beerdispenser.app;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.codesherpa.beerdispenser.app.controllers.PromoterController;
import com.codesherpa.beerdispenser.app.models.Material;
import com.codesherpa.beerdispenser.app.models.Specification;
import com.codesherpa.beerdispenser.app.services.MaterialService;
import com.codesherpa.beerdispenser.app.services.SpecificationService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class AppApplication {
    Logger logger = LoggerFactory.getLogger(AppApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

	
	@Bean
	CommandLineRunner runner(MaterialService materialService) {
		return args -> {
			// read json and write to db
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<List<Material>> typeReference = new TypeReference<List<Material>>(){};
			InputStream inputStream = TypeReference.class.getResourceAsStream("/materials.json");
			try {
				List<Material> materials = mapper.readValue(inputStream,typeReference);
				for (int i = 0; i < materials.size(); ++i){
					materialService.createMaterial(materials.get(i));
				}
				logger.info("Materials Saved!");
			} catch (IOException e){
				logger.error("Unable to save users: " + e.getMessage());
			}
		};
	}
	

	/* 
	@Bean
	CommandLineRunner runner(SpecificationService specificationService) {
		return args -> {
			// read json and write to db
			ObjectMapper mapper = new ObjectMapper();
			TypeReference<List<Specification>> typeReference = new TypeReference<List<Specification>>(){};
			InputStream inputStream = TypeReference.class.getResourceAsStream("/specification_db.json");
			try {
				List<Specification> specifications = mapper.readValue(inputStream,typeReference);
				for (int i = 0; i < specifications.size(); ++i){
					specificationService.createSpecification(specifications.get(i));
				}
				logger.info("Specification Saved!");
			} catch (IOException e){
				logger.error("Unable to save specifications: " + e.getMessage());
			}
		};
	}
	*/
}
