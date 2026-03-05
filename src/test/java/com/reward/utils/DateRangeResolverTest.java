package com.reward.utils;

import com.reward.dto.DateRange;
import com.reward.exception.InvalidDateRangeException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DateRangeResolverTest {

    @Test
    public void shouldReturnProvidedDateWhenValid() {
        LocalDate startDate = LocalDate.of(2026, 1, 1);
        LocalDate endDate = LocalDate.of(2026, 3, 3);

        DateRange result = DateRangeResolver.resolve(null, startDate, endDate);

        assertEquals(result.startDate(), startDate);
        assertEquals(result.endDate(), endDate);
    }

    @Test
    public void shouldReturnCurrentDateAsStartDateAndEndDateMinus3Month() {
        DateRange result = DateRangeResolver.resolve(null, null, null);

        assertEquals(result.startDate(), LocalDate.now().minusMonths(3));
        assertEquals(result.endDate(), LocalDate.now());
    }

    @Test
    public void shouldThrowWhenStartDateIsGreaterThanEndDate() {
        LocalDate startDate = LocalDate.of(2026, 4, 1);
        LocalDate endDate = LocalDate.of(2026, 1, 1);

        assertThrows(InvalidDateRangeException.class, () -> DateRangeResolver.resolve(null, startDate, endDate));
    }

    @Test
    void shouldAllowSameStartAndEndDate() {
        LocalDate date = LocalDate.of(2026, 1, 1);

        DateRange result = DateRangeResolver.resolve(null, date, date);

        assertEquals(date, result.startDate());
        assertEquals(date, result.endDate());
    }

    @Test
    public void shouldThrowWhenStartDateIsProvided() {
        LocalDate startDate = LocalDate.of(2026, 1, 1);
        assertThrows(InvalidDateRangeException.class, () -> DateRangeResolver.resolve(null, startDate, null));
    }

    @Test
    public void shouldThrowWhenEndDateIsProvided() {
        LocalDate endDate = LocalDate.of(2026, 3, 3);
        assertThrows(InvalidDateRangeException.class, () -> DateRangeResolver.resolve(null, null, endDate));
    }

    @Test
    public void shouldThrowWhenNoOfMonthIsProvidedWithStartDateAndEndDate() {
        Integer noOfMonth = 6;
        LocalDate startDate = LocalDate.of(2026, 1, 1);
        LocalDate endDate = LocalDate.of(2026, 3, 3);
        assertThrows(InvalidDateRangeException.class, () -> DateRangeResolver.resolve(noOfMonth, startDate, endDate));
    }

    @Test
    public void shouldThrowWhenNoOfMonthIsProvidedWithStartDateOnly() {
        Integer noOfMonth = 6;
        LocalDate startDate = LocalDate.of(2026, 1, 1);
        assertThrows(InvalidDateRangeException.class, () -> DateRangeResolver.resolve(noOfMonth, startDate, null));
    }

    @Test
    public void shouldThrowWhenNoOfMonthIsProvidedWithEndDateOnly() {
        Integer noOfMonth = 6;
        LocalDate endDate = LocalDate.of(2026, 1, 1);
        assertThrows(InvalidDateRangeException.class, () -> DateRangeResolver.resolve(noOfMonth, null, endDate));
    }

    @Test
    void shouldReturnWhenNoOfMonthIsProvided() {
        int noOfMonth = 6;
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = LocalDate.now().minusMonths(noOfMonth);

        DateRange result = DateRangeResolver.resolve(null, startDate, endDate);

        assertEquals(startDate, result.startDate());
        assertEquals(endDate, result.endDate());
    }

}