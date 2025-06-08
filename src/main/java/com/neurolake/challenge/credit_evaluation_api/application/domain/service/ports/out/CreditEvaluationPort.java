package com.neurolake.challenge.credit_evaluation_api.application.domain.service.ports.out;

import com.neurolake.challenge.credit_evaluation_api.application.domain.model.Client;
import com.neurolake.challenge.credit_evaluation_api.application.domain.model.CreditType;

import java.util.List;

public interface CreditEvaluationPort {
    List<CreditType> evaluateEligibleCreditTypes(Client client);
}
