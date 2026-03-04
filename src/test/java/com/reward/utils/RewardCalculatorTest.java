package com.reward.utils;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RewardCalculatorTest {

    @Test
    public void shouldReturnZeroPointsForAmountBelow50() {
        assertEquals(0, RewardCalculator.calculate(BigDecimal.valueOf(45.00)));
    }

    @Test
    public void shouldReturn25PointsForAmountBetween50And100() {
        assertEquals(25, RewardCalculator.calculate(BigDecimal.valueOf(75.0)));
    }

    @Test
    public void shouldReturn25PointsForAmountAbove100() {
        assertEquals(90, RewardCalculator.calculate(BigDecimal.valueOf(120.0)));
    }

    @Test
    public void shouldReturn50PointsForAmount100() {
        assertEquals(50, RewardCalculator.calculate(BigDecimal.valueOf(100.0)));
    }

    @Test
    public void shouldReturn0PointsForAmount50() {
        assertEquals(0, RewardCalculator.calculate(BigDecimal.valueOf(50.0)));
    }

    @Test
    public void shouldThrowWhenAmountIsNull() {
        assertThrows(IllegalArgumentException.class, () -> RewardCalculator.calculate(null));
    }

}