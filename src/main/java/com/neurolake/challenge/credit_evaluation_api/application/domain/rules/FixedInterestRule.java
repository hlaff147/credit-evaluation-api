package com.neurolake.challenge.credit_evaluation_api.application.domain.rules;

import com.neurolake.challenge.credit_evaluation_api.application.domain.model.Client;
import com.neurolake.challenge.credit_evaluation_api.application.domain.model.CreditType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class FixedInterestRule implements CreditEligibilityRule {
    @Override
    public Optional<CreditType> evaluate(Client client) {
        log.debug("Evaluating fixed interest eligibility for client: {}", client.getName());
        if (client.getAge() >= 18 && client.getAge() <= 25) {
            return Optional.of(CreditType.FIXED_INTEREST);
        }
        return Optional.empty();
    }
}
