package com.neurolake.challenge.credit_evaluation_api.application.domain.rules;

import com.neurolake.challenge.credit_evaluation_api.application.domain.model.Client;
import com.neurolake.challenge.credit_evaluation_api.application.domain.model.CreditType;

import java.util.Optional;

public interface CreditEligibilityRule {
    Optional<CreditType> evaluate(Client client);
}
