package com.neurolake.challenge.credit_evaluation_api.application.domain.model;

import lombok.Getter;

@Getter
public enum CreditType {
    FIXED_INTEREST("Crédito com Juros Fixos", Double.valueOf(0.05)),
    VARIABLE_INTEREST("Crédito com Juros Variáveis", null),
    CONSIGNMENT("Crédito Consignado", null),
    NOT_ELIGIBLE("Não Elegível para Crédito", null);

    private final String description;
    private final Double interestRate;

    CreditType(String description, Double interestRate) {
        this.description = description;
        this.interestRate = interestRate;
    }
}
