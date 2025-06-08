package com.neurolake.challenge.credit_evaluation_api.infrastructure.adapter.in.web.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Classe especializada para representação de erros de validação na API
 */
@Data
@AllArgsConstructor
public class ValidationErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private Map<String, String> validationErrors;
}
