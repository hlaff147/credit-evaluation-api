package com.neurolake.challenge.credit_evaluation_api.fixtures;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class TestConstants {
    private TestConstants() {}

    public static final String VALID_CPF = "12345678909";
    public static final String INVALID_CPF = "11111111111";
    public static final BigDecimal MINIMUM_INCOME = BigDecimal.valueOf(1000.00);
    public static final BigDecimal MAXIMUM_INCOME = BigDecimal.valueOf(1000000.00);
    public static final LocalDate MIN_BIRTH_DATE = LocalDate.of(1900, 1, 1);
    public static final LocalDate MAX_BIRTH_DATE = LocalDate.now().minusYears(18);
    public static final BigDecimal MINIMUM_LOAN_AMOUNT = BigDecimal.valueOf(1000.00);
    public static final BigDecimal MAXIMUM_LOAN_AMOUNT = BigDecimal.valueOf(1000000.00);
}
