package com.neurolake.challenge.credit_evaluation_api.infrastructure.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neurolake.challenge.credit_evaluation_api.application.domain.model.CreditType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientResponseDTO {
    @JsonProperty("Id")
    private Long id;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Age")
    private int age;

    @JsonProperty("Income")
    private double income;

    @JsonProperty("EligibleCreditTypes")
    private List<CreditType> eligibleCreditTypes;
}
