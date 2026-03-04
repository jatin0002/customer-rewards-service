package com.reward.dto;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

public record RewardDTO(
        Long customerId,
        String customerName,
        LocalDate startDate,
        LocalDate endDate,
        Map<YearMonth, Integer> monthlyPoints,
        Integer totalPoints,
        List<TransactionSummaryDTO> transactions
) {
}
