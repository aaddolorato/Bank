package com.accenture.bank.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
	
	 @Bean
	  public OpenAPI bankApplicationOpenAPI() {
	      return new OpenAPI()
	              .info(new Info().title("Bank Application")
	              .description("Crud Bank Application")
	              .contact(new Contact().name("Alessandra Addolorato").email("a.addolorato@accenture.com"))
	              .version("v0.0.1"));
	  }
}
