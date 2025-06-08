package com.neurolake.challenge.credit_evaluation_api.integration;

import com.neurolake.challenge.credit_evaluation_api.application.domain.model.Client;
import com.neurolake.challenge.credit_evaluation_api.fixtures.ClientFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@DisplayName("API de Avaliação de Crédito - Testes de Integração")
class CreditEvaluationApiIntegrationTest {

    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0.26")
            .withDatabaseName("credit_evaluation")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("Deve cadastrar um novo cliente")
    void deveCadastrarNovoCliente() {
        // Arrange
        Client client = ClientFixture.validClient();

        // Act
        ResponseEntity<Client> response = restTemplate.postForEntity("/api/clients", client, Client.class);

        // Assert
        assertThat(response.getStatusCode())
            .as("Status da resposta deve ser CREATED")
            .isEqualTo(HttpStatus.CREATED);
        
        assertThat(response.getBody())
            .as("Resposta não deve ser nula")
            .isNotNull();
        
        assertThat(response.getBody().getId())
            .as("ID do cliente deve ser gerado")
            .isNotNull();
    }

    @Test
    @DisplayName("Deve avaliar crédito do cliente")
    void deveAvaliarCreditoCliente() {
        // Arrange
        Client client = ClientFixture.validClient();
        Client savedClient = restTemplate.postForEntity("/api/clients", client, Client.class).getBody();
        assertThat(savedClient).isNotNull();

        // Act
        ResponseEntity<Client> response = restTemplate.getForEntity(
            "/api/clients/{id}/automotive-credit", 
            Client.class, 
            savedClient.getId()
        );

        // Assert
        assertThat(response.getStatusCode())
            .as("Status da resposta deve ser OK")
            .isEqualTo(HttpStatus.OK);
        
        assertThat(response.getBody())
            .as("Resposta não deve ser nula")
            .isNotNull();
        
        assertThat(response.getBody().getEligibleCreditTypes())
            .as("Lista de tipos de crédito elegíveis não deve ser nula")
            .isNotNull();
    }
}
