package com.reward.utils;

import java.math.BigDecimal;

public class RewardCalculator {

    public static int calculate(BigDecimal amount) {

        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }

        BigDecimal fifty = BigDecimal.valueOf(50);
        BigDecimal hundred = BigDecimal.valueOf(100);

        if (amount.compareTo(fifty) <= 0) {
            return 0;
        }

        if (amount.compareTo(hundred) <= 0) {
            return amount.subtract(fifty).intValue();
        }

        return amount.subtract(hundred)
                .multiply(BigDecimal.valueOf(2))
                .add(fifty)
                .intValue();
    }
}
