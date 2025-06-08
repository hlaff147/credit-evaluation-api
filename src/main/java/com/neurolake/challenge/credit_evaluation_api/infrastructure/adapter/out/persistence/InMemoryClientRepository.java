package com.neurolake.challenge.credit_evaluation_api.infrastructure.adapter.out.persistence;

import com.neurolake.challenge.credit_evaluation_api.application.domain.model.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Repository
public class InMemoryClientRepository {

    private final ConcurrentHashMap<Long, Client> clients = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong();

    public Client save(Client client) {
        if (client.getId() == null) {
            client.setId(idCounter.incrementAndGet());
            log.info("Generated new client ID: {}", client.getId());
        }
        clients.put(client.getId(), client);
        log.debug("Client saved in memory repository: {}", client);
        return client;
    }

    public Optional<Client> findById(Long id) {
        Client client = clients.get(id);
        log.debug("Client found by ID {}: {}", id, client);
        return Optional.ofNullable(client);
    }

    public List<Client> findAll() {
        log.debug("Returning all {} clients from memory repository", clients.size());
        return new ArrayList<>(clients.values());
    }
}
