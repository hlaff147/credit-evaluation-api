package com.neurolake.challenge.credit_evaluation_api.infrastructure.adapter.in.web.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Classe para representação padronizada de erros na API
 */
@Data
@AllArgsConstructor
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
}
