package com.neurolake.challenge.credit_evaluation_api.application.domain.service.ports.out;

import com.neurolake.challenge.credit_evaluation_api.application.domain.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepositoryPort {
    Client save(Client client);
    Optional<Client> findById(Long id);
    List<Client> findAll();
}
