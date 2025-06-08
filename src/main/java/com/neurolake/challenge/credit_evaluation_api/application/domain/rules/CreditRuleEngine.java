package com.neurolake.challenge.credit_evaluation_api.application.domain.rules;

import com.neurolake.challenge.credit_evaluation_api.application.domain.model.Client;
import com.neurolake.challenge.credit_evaluation_api.application.domain.model.CreditType;
import com.neurolake.challenge.credit_evaluation_api.application.domain.service.ports.out.CreditEvaluationPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class CreditRuleEngine implements CreditEvaluationPort {

    private final List<CreditEligibilityRule> rules;

    @Autowired
    public CreditRuleEngine(List<CreditEligibilityRule> rules) {
        this.rules = rules;
    }

    @Override
    public List<CreditType> evaluateEligibleCreditTypes(Client client) {
        log.info("Evaluating credit eligibility for client: {}", client.getName());
        Set<CreditType> eligibleTypes = new HashSet<>();

        for (CreditEligibilityRule rule : rules) {
            rule.evaluate(client).ifPresent(eligibleTypes::add);
        }

        if (eligibleTypes.isEmpty()) {
            log.info("Client {} is not eligible for any credit type", client.getName());
            eligibleTypes.add(CreditType.NOT_ELIGIBLE);
        } else {
            log.info("Client {} is eligible for credit types: {}", client.getName(), eligibleTypes);
        }

        return new ArrayList<>(eligibleTypes);
    }
}
