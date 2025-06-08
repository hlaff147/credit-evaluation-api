package com.neurolake.challenge.credit_evaluation_api.application.domain.service;

import com.neurolake.challenge.credit_evaluation_api.application.domain.model.Client;
import com.neurolake.challenge.credit_evaluation_api.application.domain.model.CreditType;
import com.neurolake.challenge.credit_evaluation_api.application.domain.service.ports.out.AutomotiveCreditPort;
import com.neurolake.challenge.credit_evaluation_api.application.domain.service.ports.out.ClientRepositoryPort;
import com.neurolake.challenge.credit_evaluation_api.application.domain.service.ports.out.CreditEvaluationPort;
import com.neurolake.challenge.credit_evaluation_api.application.ports.in.EvaluateAutomotiveCreditUseCase;
import com.neurolake.challenge.credit_evaluation_api.application.ports.in.EvaluateCreditUseCase;
import com.neurolake.challenge.credit_evaluation_api.application.ports.in.FindEligibleFixedHatchClientsUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreditService implements
        EvaluateCreditUseCase,
        EvaluateAutomotiveCreditUseCase,
        FindEligibleFixedHatchClientsUseCase {

    private final ClientRepositoryPort clientRepositoryPort;
    private final CreditEvaluationPort creditEvaluationPort;
    private final AutomotiveCreditPort automotiveCreditPort;

    @Override
    public List<CreditType> evaluateClientCreditTypes(Client client) {
        log.info("Evaluating credit types for client: {}", client.getName());
        return creditEvaluationPort.evaluateEligibleCreditTypes(client);
    }

    @Override
    public boolean isEligibleForHatchCredit(Client client) {
        log.info("Evaluating Hatch credit eligibility for client: {}", client.getName());
        return automotiveCreditPort.isEligibleForHatchCredit(client);
    }

    @Override
    public boolean isEligibleForSUVCredit(Client client) {
        log.info("Evaluating SUV credit eligibility for client: {}", client.getName());
        return automotiveCreditPort.isEligibleForSUVCredit(client);
    }

    @Override
    public List<Client> findClientsEligibleForFixedAndHatch() {
        log.info("Finding clients eligible for Fixed Interest credit and Hatch vehicle");
        return clientRepositoryPort.findAll().stream()
                .filter(client -> client.getAge() >= 23 && client.getAge() <= 49)
                .filter(client -> client.getEligibleCreditTypes().contains(CreditType.FIXED_INTEREST))
                .filter(this::isEligibleForHatchCredit)
                .toList();
    }
}