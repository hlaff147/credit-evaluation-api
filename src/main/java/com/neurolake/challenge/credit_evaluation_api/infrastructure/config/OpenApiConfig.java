package com.neurolake.challenge.credit_evaluation_api.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Avaliação de Crédito")
                        .version("1.0")
                        .description("API para avaliação de elegibilidade de crédito e crédito automotivo")
                        .contact(new Contact()
                                .name("NeuroLake")
                                .email("contato@neurolake.com")));
    }
}
