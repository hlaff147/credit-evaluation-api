package com.neurolake.challenge.credit_evaluation_api.unit.rules;

import com.neurolake.challenge.credit_evaluation_api.application.domain.model.Client;
import com.neurolake.challenge.credit_evaluation_api.application.domain.model.CreditType;
import com.neurolake.challenge.credit_evaluation_api.application.domain.rules.FixedInterestRule;
import com.neurolake.challenge.credit_evaluation_api.fixtures.ClientFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Testes da Regra de Juros Fixos")
class FixedInterestRuleTest {

    private final FixedInterestRule rule = new FixedInterestRule();

    @Test
    @DisplayName("Deve retornar elegível quando cliente tem entre 18 e 25 anos")
    void deveRetornarElegivelQuandoClienteTemIdadeAdequada() {
        // Arrange
        Client cliente = ClientFixture.clienteJovemFixo()
                .age(20)
                .build();

        // Act
        Optional<CreditType> resultado = rule.evaluate(cliente);

        // Assert
        assertThat(resultado)
                .isPresent()
                .hasValue(CreditType.FIXED_INTEREST);
    }

    @ParameterizedTest
    @DisplayName("Deve retornar não elegível para idades fora do intervalo permitido")
    @CsvSource({
        "17, 'muito jovem'",
        "26, 'muito velho'"
    })
    void deveRetornarNaoElegivelParaIdadesForaDoIntervalo(int idade, String descricao) {
        // Arrange
        Client cliente = ClientFixture.clienteJovemFixo()
                .age(idade)
                .build();

        // Act
        Optional<CreditType> resultado = rule.evaluate(cliente);

        // Assert
        assertThat(resultado)
                .as("Cliente com %d anos (%s) não deveria ser elegível", idade, descricao)
                .isEmpty();
    }
}
