package com.neurolake.challenge.credit_evaluation_api.application.ports.in;

import com.neurolake.challenge.credit_evaluation_api.application.domain.model.Client;

import java.util.List;

public interface GetAllClientsUseCase {
    List<Client> getAllClients();
}
