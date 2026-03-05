package com.reward.utils;

import com.reward.dto.DateRange;
import com.reward.exception.InvalidDateRangeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class DateRangeResolver {

    private static final Logger log = LoggerFactory.getLogger(DateRangeResolver.class);

    private final static Integer DEFAULT_MONTH = 3;

    public static DateRange resolve(Integer noOfMonths, LocalDate startDate, LocalDate endDate) {

        if (noOfMonths == null && startDate == null && endDate == null) {
            log.info("No date range provided. Defaulting to last {} months.", DEFAULT_MONTH);
            return customDateRangeGeneratorByMonths(DEFAULT_MONTH);
        }

        if (noOfMonths != null) {

            if (startDate != null || endDate != null) {
                log.warn("Invalid request: noOfMonth cannot be used with startDate/endDate");
                throw new InvalidDateRangeException(
                        "Provide either 'noOfMonth' OR 'startDate and endDate', not both."
                );
            }

            if (noOfMonths <= 0) {
                log.warn("Invalid number of month value: {}", noOfMonths);
                throw new InvalidDateRangeException(
                        "noOfMonth must be greater than 0"
                );
            }

            log.debug("Generating date range for {} of months", noOfMonths);
            return customDateRangeGeneratorByMonths(noOfMonths);
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

    private static DateRange customDateRangeGeneratorByMonths(Integer noOfMonths) {

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusMonths(noOfMonths);

        return new DateRange(startDate, endDate);
    }
}
