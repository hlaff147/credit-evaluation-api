package com.neurolake.challenge.credit_evaluation_api.application.domain.service;

import com.neurolake.challenge.credit_evaluation_api.application.domain.model.Client;
import com.neurolake.challenge.credit_evaluation_api.application.domain.model.CreditType;
import com.neurolake.challenge.credit_evaluation_api.application.domain.service.ports.out.ClientRepositoryPort;
import com.neurolake.challenge.credit_evaluation_api.application.domain.service.ports.out.CreditEvaluationPort;
import com.neurolake.challenge.credit_evaluation_api.application.ports.in.GetAllClientsUseCase;
import com.neurolake.challenge.credit_evaluation_api.application.ports.in.GetClientUseCase;
import com.neurolake.challenge.credit_evaluation_api.application.ports.in.RegisterClientUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService implements RegisterClientUseCase, GetClientUseCase, GetAllClientsUseCase {

    private final ClientRepositoryPort clientRepository;
    private final CreditEvaluationPort creditEvaluationPort;

    @Override
    public Client registerClient(Client client) {
        log.info("Registering client: {}", client.getName());

        List<CreditType> eligibleCredits = creditEvaluationPort.evaluateEligibleCreditTypes(client);
        client.setEligibleCreditTypes(eligibleCredits);

        Client savedClient = clientRepository.save(client);
        log.info("Client registered successfully with ID: {}", savedClient.getId());

        return savedClient;
    }

    @Override
    public Optional<Client> getClientById(Long id) {
        log.info("Fetching client with id: {}", id);
        return clientRepository.findById(id);
    }

    @Override
    public List<Client> getAllClients() {
        log.info("Fetching all clients");
        return clientRepository.findAll();
    }
}