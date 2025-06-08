package com.neurolake.challenge.credit_evaluation_api.fixtures;

import java.util.Random;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.math.BigDecimal;

public abstract class BaseFixture {
    protected static final Random random = new Random();

    protected static String randomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    protected static BigDecimal randomBigDecimal(BigDecimal min, BigDecimal max) {
        BigDecimal range = max.subtract(min);
        BigDecimal value = min.add(range.multiply(BigDecimal.valueOf(random.nextDouble())));
        return value.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    protected static LocalDate randomDate(LocalDate start, LocalDate end) {
        long startEpochDay = start.toEpochDay();
        long endEpochDay = end.toEpochDay();
        long randomDay = startEpochDay + random.nextInt((int) (endEpochDay - startEpochDay));
        return LocalDate.ofEpochDay(randomDay);
    }

    protected static LocalDateTime randomDateTime(LocalDateTime start, LocalDateTime end) {
        long startSeconds = start.toEpochSecond(java.time.ZoneOffset.UTC);
        long endSeconds = end.toEpochSecond(java.time.ZoneOffset.UTC);
        long randomSeconds = startSeconds + random.nextInt((int) (endSeconds - startSeconds));
        return LocalDateTime.ofEpochSecond(randomSeconds, 0, java.time.ZoneOffset.UTC);
    }
}
