package com.neurolake.challenge.credit_evaluation_api.component;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neurolake.challenge.credit_evaluation_api.infrastructure.adapter.in.web.dto.ClientRequestDTO;
import com.neurolake.challenge.credit_evaluation_api.infrastructure.adapter.in.web.dto.ClientResponseDTO;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("API de Avaliação de Crédito - Testes de Componente")
class CreditEvaluationComponentTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve processar fluxo completo de avaliação de crédito")
    void deveProcessarFluxoCompletoAvaliacaoCredito() throws Exception {
        // Arrange
        ClientRequestDTO request = ClientRequestDTO.builder()
                .name("João da Silva")
                .age(35)
                .income(10000.0)
                .build();

        // Act & Assert - Cadastro do cliente
        String responseJson = mockMvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", org.hamcrest.Matchers.matchesPattern(".*/api/clients/\\d+")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(request.getName()))
                .andExpect(jsonPath("$.age").value(request.getAge()))
                .andExpect(jsonPath("$.income").value(request.getIncome()))
                .andExpect(jsonPath("$.eligibleCreditTypes").isArray())
                .andReturn().getResponse().getContentAsString();

        // Extract client ID from response
        ClientResponseDTO savedClient = objectMapper.readValue(responseJson, ClientResponseDTO.class);

        // Act & Assert - Avaliação de crédito
        mockMvc.perform(get("/api/clients/{id}/automotive-credit", savedClient.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientName").value(request.getName()))
                .andExpect(jsonPath("$.eligibleForHatch").exists())
                .andExpect(jsonPath("$.eligibleForSUV").exists());
    }

    @Test
    @DisplayName("Deve rejeitar cliente com dados inválidos")
    void deveRejeitarClienteDadosInvalidos() throws Exception {
        // Arrange
        ClientRequestDTO request = ClientRequestDTO.builder()
                .name("")  // nome inválido
                .age(-1)   // idade inválida
                .income(-1000.0)  // renda inválida
                .build();

        // Act & Assert
        mockMvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Validation Error"))
                .andExpect(jsonPath("$.validationErrors").exists())
                .andExpect(jsonPath("$.validationErrors.name").value("Name must be between 3 and 100 characters"))
                .andExpect(jsonPath("$.validationErrors.age").value("Age must be a positive number"))
                .andExpect(jsonPath("$.validationErrors.income").value("Income must be a positive value"));
    }

    @Test
    @DisplayName("Deve retornar NOT_FOUND para cliente inexistente")
    void deveRetornarNotFoundClienteInexistente() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/clients/{id}/automotive-credit", 999L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Resource Not Found"));
    }


    @Test
    @DisplayName("Deve rejeitar cliente com idade negativa")
    void deveRejeitarClienteIdadeNegativa() throws Exception {
        ClientRequestDTO request = ClientRequestDTO.builder()
                .name("Maria")
                .age(-5)
                .income(5000.0)
                .build();
        mockMvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validationErrors.age").value("Age must be a positive number"));
    }

    @Test
    @DisplayName("Deve rejeitar cliente com renda negativa")
    void deveRejeitarClienteRendaNegativa() throws Exception {
        ClientRequestDTO request = ClientRequestDTO.builder()
                .name("Carlos")
                .age(40)
                .income(-100.0)
                .build();
        mockMvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validationErrors.income").value("Income must be a positive value"));
    }

    @Test
    @DisplayName("Deve rejeitar múltiplos campos inválidos e validar todas as mensagens de erro")
    void deveRejeitarMultiplosCamposInvalidos() throws Exception {
        ClientRequestDTO request = ClientRequestDTO.builder()
                .name("A") 
                .age(-10) 
                .income(-500.0)
                .build();
        mockMvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validationErrors.name").exists())
                .andExpect(jsonPath("$.validationErrors.age").exists())
                .andExpect(jsonPath("$.validationErrors.income").exists());
    }

    @Test
    @DisplayName("Deve rejeitar nome com mais de 100 caracteres")
    void deveRejeitarNomeMuitoLongo() throws Exception {
        String longName = "A".repeat(101);
        ClientRequestDTO request = ClientRequestDTO.builder()
                .name(longName)
                .age(30)
                .income(5000.0)
                .build();
        mockMvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validationErrors.name").value("Name must be between 3 and 100 characters"));
    }

    @Test
    @DisplayName("Deve rejeitar renda extremamente negativa")
    void deveRejeitarRendaExtremamenteNegativa() throws Exception {
        ClientRequestDTO request = ClientRequestDTO.builder()
                .name("Cliente Teste")
                .age(40)
                .income(-1_000_000.0)
                .build();
        mockMvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validationErrors.income").value("Income must be a positive value"));
    }

    @Test
    @DisplayName("Deve aceitar renda extremamente alta")
    void deveAceitarRendaExtremamenteAlta() throws Exception {
        ClientRequestDTO request = ClientRequestDTO.builder()
                .name("Cliente Rico")
                .age(40)
                .income(1_000_000_000.0)
                .build();
        mockMvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.income").value(1_000_000_000.0));
    }
}
