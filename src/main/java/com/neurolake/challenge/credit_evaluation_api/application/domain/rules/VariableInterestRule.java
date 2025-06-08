package com.neurolake.challenge.credit_evaluation_api.application.domain.rules;

import com.neurolake.challenge.credit_evaluation_api.application.domain.model.Client;
import com.neurolake.challenge.credit_evaluation_api.application.domain.model.CreditType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class VariableInterestRule implements CreditEligibilityRule {
    @Override
    public Optional<CreditType> evaluate(Client client) {
        log.debug("Evaluating variable interest eligibility for client: {}", client.getName());
        if (client.getAge() >= 21 && client.getAge() <= 65 &&
            client.getIncome() >= 5000.00 && client.getIncome() <= 15000.00) {
            return Optional.of(CreditType.VARIABLE_INTEREST);
        }
        return Optional.empty();
    }
}
