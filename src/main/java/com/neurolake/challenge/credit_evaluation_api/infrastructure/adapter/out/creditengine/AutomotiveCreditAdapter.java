package com.neurolake.challenge.credit_evaluation_api.infrastructure.adapter.out.creditengine;

import com.neurolake.challenge.credit_evaluation_api.application.domain.model.Client;
import com.neurolake.challenge.credit_evaluation_api.application.domain.service.ports.out.AutomotiveCreditPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AutomotiveCreditAdapter implements AutomotiveCreditPort {

    @Override
    public boolean isEligibleForHatchCredit(Client client) {
        log.debug("Evaluating Hatch credit eligibility for client: {}", client.getName());
        boolean isEligible = client.getIncome() >= 5000.00 && client.getIncome() <= 15000.00;
        log.debug("Client {} is {} eligible for Hatch credit", client.getName(), isEligible ? "" : "not");
        return isEligible;
    }

    @Override
    public boolean isEligibleForSUVCredit(Client client) {
        log.debug("Evaluating SUV credit eligibility for client: {}", client.getName());
        boolean isEligible = client.getIncome() > 8000.00 && client.getAge() > 20;
        log.debug("Client {} is {} eligible for SUV credit", client.getName(), isEligible ? "" : "not");
        return isEligible;
    }
}
