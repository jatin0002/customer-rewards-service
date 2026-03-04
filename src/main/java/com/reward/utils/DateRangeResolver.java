package com.reward.utils;

import com.reward.dto.DateRange;
import com.reward.exception.InvalidDateRangeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class DateRangeResolver {

    private static final Logger log = LoggerFactory.getLogger(DateRangeResolver.class);

    public static DateRange resolve(LocalDate startDate, LocalDate endDate) {

        if (startDate == null && endDate == null) {
            log.info("No date range provided. Defaulting to last 3 months.");
            return defaultLastThreeMonths();
        }

        if (startDate == null || endDate == null) {
            log.warn("Invalid date range provided: startDate={}, endDate={}", startDate, endDate);
            throw new InvalidDateRangeException(
                    "Both startDate and endDate must be provided together"
            );
        }

        if (startDate.isAfter(endDate)) {
            log.warn("Invalid date range provided: startDate={}, endDate={}", startDate, endDate);
            throw new InvalidDateRangeException(
                    "startDate must be less than or equal to endDate"
            );
        }

        log.debug("Resolving date range: startDate={}, endDate={}", startDate, endDate);
        return new DateRange(startDate, endDate);
    }

    private static DateRange defaultLastThreeMonths() {

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusMonths(3);

        return new DateRange(startDate, endDate);
    }
}
