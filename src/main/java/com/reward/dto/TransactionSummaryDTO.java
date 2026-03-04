package com.reward.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionSummaryDTO(
        Long transactionId,
        BigDecimal amount,
        Integer rewardPoints,
        LocalDate transactionDate
) {
}
