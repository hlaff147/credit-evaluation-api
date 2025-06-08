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
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("age")
    private int age;

    @JsonProperty("income")
    private double income;

    @JsonProperty("eligibleCreditTypes")
    private List<CreditType> eligibleCreditTypes;
}
