package com.neurolake.challenge.credit_evaluation_api.application.ports.in;

import com.neurolake.challenge.credit_evaluation_api.application.domain.model.Client;

public interface EvaluateAutomotiveCreditUseCase {
    boolean isEligibleForHatchCredit(Client client);
    boolean isEligibleForSUVCredit(Client client);
}
