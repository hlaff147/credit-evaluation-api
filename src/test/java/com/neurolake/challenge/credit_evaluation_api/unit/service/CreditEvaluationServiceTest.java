package com.neurolake.challenge.credit_evaluation_api.unit.service;

import com.neurolake.challenge.credit_evaluation_api.application.domain.model.Client;
import com.neurolake.challenge.credit_evaluation_api.application.domain.model.CreditType;
import com.neurolake.challenge.credit_evaluation_api.fixtures.ClientFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

@DisplayName("Avaliação de Crédito - Testes Unitários")
class CreditEvaluationServiceTest {

    @Nested
    @DisplayName("Avaliação por Idade")
    class AgeEvaluationTests {
        
        @ParameterizedTest(name = "Cliente com {0} anos deve ser {1} para crédito")
        @CsvSource({
            "17, 'inelegível'",
            "18, 'elegível'",
            "65, 'elegível'",
            "66, 'inelegível'"
        })
        void deveValidarElegibilidadePorIdade(int age, String expectedResult) {
            // Arrange
            Client client = ClientFixture.clientWithAge(age);
            boolean expected = "elegível".equals(expectedResult);
            
            // Act & Assert
            assertThat(isEligibleByAge(client))
                .as("Cliente com %d anos deve ser %s", age, expectedResult)
                .isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("Avaliação por Renda")
    class IncomeEvaluationTests {
        
        @ParameterizedTest(name = "Cliente com renda {0} deve ser elegível para {1}")
        @CsvSource({
            "3000.0, 'FIXED_INTEREST'",
            "5000.0, 'VARIABLE_INTEREST'",
            "10000.0, 'CONSIGNMENT'"
        })
        void deveDefinirTipoDeCreditoPorRenda(double income, CreditType expectedType) {
            // Arrange
            Client client = ClientFixture.clientWithIncome(income);
            
            // Act & Assert
            assertThat(determineEligibleCreditTypes(client))
                .as("Cliente com renda %.2f deve ser elegível para %s", income, expectedType)
                .contains(expectedType);
        }
    }

    @Nested
    @DisplayName("Validação de Nome")
    class NameValidationTests {
        
        @ParameterizedTest(name = "Nome {0} deve ser considerado inválido")
        @ValueSource(strings = {"", " ", "   "})
        void deveRejeitarNomesInvalidos(String name) {
            // Arrange
            Client client = ClientFixture.clientWithName(name);
            
            // Act & Assert
            assertThat(isValidName(client.getName()))
                .as("Nome '%s' deve ser considerado inválido", name)
                .isFalse();
        }
        
        @Test
        @DisplayName("Deve aceitar nome válido")
        void deveAceitarNomeValido() {
            // Arrange
            String validName = "João da Silva";
            Client client = ClientFixture.clientWithName(validName);
            
            // Act & Assert
            assertThat(isValidName(client.getName()))
                .as("Nome '%s' deve ser considerado válido", validName)
                .isTrue();
        }
    }

    @Nested
    @DisplayName("Validação de Nome - Casos de Borda")
    class NameEdgeCases {
        @ParameterizedTest(name = "Nome inválido: {0}")
        @CsvSource({
            "null",
            "A",
            "'A'.repeat(101)"
        })
        void deveRejeitarNomesDeBorda(String name) {
            Client client;
            if ("null".equals(name)) {
                client = ClientFixture.clientWithNullName();
            } else if ("A".equals(name)) {
                client = ClientFixture.clientWithShortName();
            } else if ("'A'.repeat(101)".equals(name)) {
                client = ClientFixture.clientWithLongName();
            } else {
                client = ClientFixture.clientWithSpecialCharName();
            }
            assertThat(isValidName(client.getName())).isFalse();
        }
    }

    @Nested
    @DisplayName("Validação de Idade - Casos de Borda")
    class AgeEdgeCases {
        @Test
        void deveRejeitarIdadeNegativa() {
            Client client = ClientFixture.clientWithNegativeAge();
            assertThat(isEligibleByAge(client)).isFalse();
        }
        @Test
        void deveRejeitarIdadeZero() {
            Client client = ClientFixture.clientWithZeroAge();
            assertThat(isEligibleByAge(client)).isFalse();
        }
        @Test
        void deveRejeitarIdadeMuitoAlta() {
            Client client = ClientFixture.clientWithHighAge();
            assertThat(isEligibleByAge(client)).isFalse();
        }
    }

    @Nested
    @DisplayName("Validação de Renda - Casos de Borda")
    class IncomeEdgeCases {
        @Test
        void deveRejeitarRendaNegativa() {
            Client client = ClientFixture.clientWithNegativeIncome();
            assertThat(determineEligibleCreditTypes(client)).isEmpty();
        }
        @Test
        void deveRejeitarRendaZero() {
            Client client = ClientFixture.clientWithZeroIncome();
            assertThat(determineEligibleCreditTypes(client)).isEmpty();
        }
        @Test
        void deveAceitarRendaExtremamenteAlta() {
            Client client = ClientFixture.clientWithExtremeHighIncome();
            assertThat(determineEligibleCreditTypes(client)).isNotEmpty();
        }
    }

    private boolean isEligibleByAge(Client client) {
        return client.getAge() >= 18 && client.getAge() <= 65;
    }

    private boolean isValidName(String name) {
        return name != null && name.trim().length() >= 3 && name.trim().length() <= 100;
    }

    private List<CreditType> determineEligibleCreditTypes(Client client) {
        List<CreditType> eligibleTypes = new ArrayList<>();
        if (client.getIncome() >= 3000.0) eligibleTypes.add(CreditType.FIXED_INTEREST);
        if (client.getIncome() >= 5000.0) eligibleTypes.add(CreditType.VARIABLE_INTEREST);
        if (client.getIncome() >= 10000.0) eligibleTypes.add(CreditType.CONSIGNMENT);
        return eligibleTypes;
    }
}
