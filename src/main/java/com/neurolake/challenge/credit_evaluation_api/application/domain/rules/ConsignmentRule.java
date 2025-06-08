package com.neurolake.challenge.credit_evaluation_api.application.domain.rules;

import com.neurolake.challenge.credit_evaluation_api.application.domain.model.Client;
import com.neurolake.challenge.credit_evaluation_api.application.domain.model.CreditType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class ConsignmentRule implements CreditEligibilityRule {
    @Override
    public Optional<CreditType> evaluate(Client client) {
        log.debug("Evaluating consignment eligibility for client: {}", client.getName());
        if (client.getAge() > 65) {
            return Optional.of(CreditType.CONSIGNMENT);
        }
        return Optional.empty();
    }
}
