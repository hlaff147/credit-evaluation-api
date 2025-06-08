package com.neurolake.challenge.credit_evaluation_api.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequestDTO {
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    private String name;

    @Min(value = 0, message = "Age must be a positive number")
    private Integer age;

    @NotNull(message = "Income is required")
    @DecimalMin(value = "0.0", message = "Income must be a positive value")
    private Double income;
}
