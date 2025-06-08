package com.neurolake.challenge.credit_evaluation_api.infrastructure.adapter.in.web;

import com.neurolake.challenge.credit_evaluation_api.application.domain.model.Client;
import com.neurolake.challenge.credit_evaluation_api.application.domain.model.CreditType;
import com.neurolake.challenge.credit_evaluation_api.application.ports.in.*;
import com.neurolake.challenge.credit_evaluation_api.infrastructure.adapter.in.web.dto.AutomotiveCreditEligibilityResponse;
import com.neurolake.challenge.credit_evaluation_api.infrastructure.adapter.in.web.dto.ClientRequestDTO;
import com.neurolake.challenge.credit_evaluation_api.infrastructure.adapter.in.web.dto.ClientResponseDTO;
import com.neurolake.challenge.credit_evaluation_api.infrastructure.adapter.in.web.dto.ClientSummaryDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
@Tag(name = "Client API", description = "API para gerenciamento de clientes e avaliação de crédito")
public class ClientController {

    private final RegisterClientUseCase registerClientUseCase;
    private final GetClientUseCase getClientUseCase;
    private final GetAllClientsUseCase getAllClientsUseCase;
    private final EvaluateCreditUseCase evaluateCreditUseCase;
    private final EvaluateAutomotiveCreditUseCase evaluateAutomotiveCreditUseCase;
    private final FindEligibleFixedHatchClientsUseCase findEligibleFixedHatchClientsUseCase;

    @PostMapping
    @Operation(summary = "Registrar um novo cliente", description = "Cria um novo cliente e avalia sua elegibilidade para diferentes tipos de crédito")
    public ResponseEntity<ClientResponseDTO> registerClient(@Valid @RequestBody ClientRequestDTO requestDTO) {
        log.info("Received request to register client: {}", requestDTO.getName());

        Client client = Client.builder()
                .name(requestDTO.getName())
                .age(requestDTO.getAge())
                .income(requestDTO.getIncome())
                .build();

        List<CreditType> eligibleTypes = evaluateCreditUseCase.evaluateClientCreditTypes(client);
        client.setEligibleCreditTypes(eligibleTypes);
        
        Client savedClient = registerClientUseCase.registerClient(client);
        
        ClientResponseDTO response = ClientResponseDTO.builder()
                .id(savedClient.getId())
                .name(savedClient.getName())
                .age(savedClient.getAge())
                .income(savedClient.getIncome())
                .eligibleCreditTypes(savedClient.getEligibleCreditTypes())
                .build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedClient.getId())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter cliente por ID", description = "Retorna os detalhes de um cliente específico incluindo seus tipos de crédito elegíveis")
    public ResponseEntity<ClientResponseDTO> getClientById(@PathVariable Long id) {
        log.info("Received request to get client with ID: {}", id);

        return getClientUseCase.getClientById(id)
                .map(client -> ClientResponseDTO.builder()
                        .id(client.getId())
                        .name(client.getName())
                        .age(client.getAge())
                        .income(client.getIncome())
                        .eligibleCreditTypes(client.getEligibleCreditTypes())
                        .build())
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + id));
    }

    @GetMapping("/{clientId}/automotive-credit")
    @Operation(summary = "Avaliar elegibilidade para crédito automotivo", description = "Verifica se um cliente é elegível para crédito de veículos Hatch e SUV")
    public ResponseEntity<AutomotiveCreditEligibilityResponse> getAutomotiveCreditEligibility(@PathVariable Long clientId) {
        log.info("Received request to evaluate automotive credit for client ID: {}", clientId);

        Client client = getClientUseCase.getClientById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + clientId));

        boolean eligibleForHatch = evaluateAutomotiveCreditUseCase.isEligibleForHatchCredit(client);
        boolean eligibleForSUV = evaluateAutomotiveCreditUseCase.isEligibleForSUVCredit(client);

        AutomotiveCreditEligibilityResponse response = AutomotiveCreditEligibilityResponse.builder()
                .clientName(client.getName())
                .eligibleForHatch(eligibleForHatch)
                .eligibleForSUV(eligibleForSUV)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/eligible-fixed-hatch")
    @Operation(summary = "Encontrar clientes elegíveis para crédito fixo e Hatch",
               description = "Lista todos os clientes entre 23-49 anos, elegíveis para crédito com juros fixos e veículos Hatch")
    public ResponseEntity<List<ClientSummaryDTO>> getEligibleClientsForFixedAndHatch() {
        log.info("Received request to find clients eligible for fixed interest credit and Hatch vehicles");

        List<Client> clients = findEligibleFixedHatchClientsUseCase.findClientsEligibleForFixedAndHatch();
        List<ClientSummaryDTO> summaryList = clients.stream()
                .map(client -> ClientSummaryDTO.builder()
                        .name(client.getName())
                        .income(client.getIncome())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(summaryList);
    }

    @GetMapping
    @Operation(summary = "Listar todos os clientes", description = "Retorna a lista de todos os clientes cadastrados")
    public ResponseEntity<List<ClientResponseDTO>> getAllClients() {
        log.info("Received request to get all clients");

        List<Client> clients = getAllClientsUseCase.getAllClients();
        List<ClientResponseDTO> clientResponses = clients.stream()
                .map(client -> ClientResponseDTO.builder()
                        .id(client.getId())
                        .name(client.getName())
                        .age(client.getAge())
                        .income(client.getIncome())
                        .eligibleCreditTypes(client.getEligibleCreditTypes())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(clientResponses);
    }
}
