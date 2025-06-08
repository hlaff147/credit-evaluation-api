package com.neurolake.challenge.credit_evaluation_api.application.ports.in;

import com.neurolake.challenge.credit_evaluation_api.application.domain.model.Client;

import java.util.Optional;

public interface GetClientUseCase {
    Optional<Client> getClientById(Long id);
}
