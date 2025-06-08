package com.neurolake.challenge.credit_evaluation_api.infrastructure.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientSummaryDTO {
    @JsonProperty("Name")
    private String name;

    @JsonProperty("Income")
    private double income;
}
