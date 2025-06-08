package com.neurolake.challenge.credit_evaluation_api.infrastructure.adapter.out.persistence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.neurolake.challenge.credit_evaluation_api.application.domain.model.Client;
import com.neurolake.challenge.credit_evaluation_api.application.domain.service.ports.out.ClientRepositoryPort;
import com.neurolake.challenge.credit_evaluation_api.infrastructure.adapter.out.persistence.entity.ClientEntity;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ClientPersistenceAdapter implements ClientRepositoryPort {

    private final ClientRepository clientRepository;

    @Override
    public Client save(Client client) {
        ClientEntity entity = ClientEntity.fromDomain(client);
        ClientEntity savedEntity = clientRepository.save(entity);
        return savedEntity.toDomain();
    }

    @Override
    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id)
                .map(ClientEntity::toDomain);
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll().stream()
                .map(ClientEntity::toDomain)
                .collect(Collectors.toList());
    }

}
