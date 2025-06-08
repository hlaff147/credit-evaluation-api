package com.neurolake.challenge.credit_evaluation_api.application.ports.in;

import com.neurolake.challenge.credit_evaluation_api.application.domain.model.Client;
import com.neurolake.challenge.credit_evaluation_api.application.domain.model.CreditType;

import java.util.List;

public interface EvaluateCreditUseCase {
    List<CreditType> evaluateClientCreditTypes(Client client);
}
