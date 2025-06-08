package com.neurolake.challenge.credit_evaluation_api.infrastructure.adapter.out.persistence;

import com.neurolake.challenge.credit_evaluation_api.application.domain.model.Client;
import com.neurolake.challenge.credit_evaluation_api.application.domain.service.ports.out.ClientRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClientRepositoryAdapter implements ClientRepositoryPort {

    private final InMemoryClientRepository inMemoryClientRepository;

    @Override
    public Client save(Client client) {
        log.debug("Saving client to repository: {}", client.getName());
        return inMemoryClientRepository.save(client);
    }

    @Override
    public Optional<Client> findById(Long id) {
        log.debug("Finding client by ID: {}", id);
        return inMemoryClientRepository.findById(id);
    }

    @Override
    public List<Client> findAll() {
        log.debug("Fetching all clients");
        return inMemoryClientRepository.findAll();
    }
}
