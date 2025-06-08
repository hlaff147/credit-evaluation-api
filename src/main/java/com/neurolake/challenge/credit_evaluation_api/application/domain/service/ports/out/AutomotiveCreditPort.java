package com.neurolake.challenge.credit_evaluation_api.application.domain.service.ports.out;

import com.neurolake.challenge.credit_evaluation_api.application.domain.model.Client;

public interface AutomotiveCreditPort {
    boolean isEligibleForHatchCredit(Client client);
    boolean isEligibleForSUVCredit(Client client);
}
