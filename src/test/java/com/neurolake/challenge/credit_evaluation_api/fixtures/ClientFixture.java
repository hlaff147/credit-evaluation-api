package com.neurolake.challenge.credit_evaluation_api.fixtures;

import com.neurolake.challenge.credit_evaluation_api.application.domain.model.Client;
import com.neurolake.challenge.credit_evaluation_api.application.domain.model.CreditType;
import static com.neurolake.challenge.credit_evaluation_api.fixtures.TestConstants.*;
import java.util.ArrayList;
import java.util.List;

public class ClientFixture extends BaseFixture {
    
    public static Client.ClientBuilder clienteJovemFixo() {
        return Client.builder()
                .name("João Silva")
                .age(20)
                .income(3000.0)
                .eligibleCreditTypes(new ArrayList<>());
    }
    
    public static Client.ClientBuilder clienteRendaVariavel() {
        return Client.builder()
                .name("Maria Santos")
                .age(35)
                .income(8000.0)
                .eligibleCreditTypes(List.of(CreditType.VARIABLE_INTEREST));
    }
    
    public static Client.ClientBuilder clienteAltaRenda() {
        return Client.builder()
                .name("José Oliveira")
                .age(45)
                .income(20000.0)
                .eligibleCreditTypes(new ArrayList<>());
    }

    public static Client.ClientBuilder clienteElegivelHatch() {
        return Client.builder()
                .name("Ana Pereira")
                .age(30)
                .income(10000.0)
                .eligibleCreditTypes(new ArrayList<>());
    }
    
    public static Client.ClientBuilder validClientBuilder() {
        return Client.builder()
                .name(randomString(10))
                .age(random.nextInt(18, 100))
                .income(randomBigDecimal(MINIMUM_INCOME, MAXIMUM_INCOME).doubleValue())
                .eligibleCreditTypes(new ArrayList<>());
    }

    public static Client validClient() {
        return validClientBuilder().build();
    }

    public static Client clientWithIncome(double income) {
        return validClientBuilder()
                .income(income)
                .build();
    }

    public static Client clientWithAge(int age) {
        return validClientBuilder()
                .age(age)
                .build();
    }

    public static Client clientWithName(String name) {
        return validClientBuilder()
                .name(name)
                .build();
    }
}
