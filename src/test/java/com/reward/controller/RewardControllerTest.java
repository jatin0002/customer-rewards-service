package com.reward.controller;

import com.reward.dto.DateRange;
import com.reward.dto.RewardDTO;
import com.reward.dto.TransactionSummaryDTO;
import com.reward.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RewardController.class)
class RewardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransactionService transactionService;

    private DateRange dateRange;
    private Map<YearMonth, Integer> monthMap;

    @BeforeEach
    void setup() {
        dateRange = new DateRange(
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 3, 3)
        );

        monthMap = new HashMap<>();
        monthMap.put(YearMonth.from(LocalDate.of(2026, 1, 4)), 90);
        monthMap.put(YearMonth.from(LocalDate.of(2026, 1, 14)), 10);
        monthMap.put(YearMonth.from(LocalDate.of(2026, 2, 24)), 40);
        monthMap.put(YearMonth.from(LocalDate.of(2026, 3, 23)), 60);
    }

    @Test
    void shouldReturnRewardSuccessfully() throws Exception {
        Long customerId = 1L;
        List<TransactionSummaryDTO> transactions = List.of(
                new TransactionSummaryDTO(1L, BigDecimal.valueOf(120.00), 90, LocalDate.of(2026, 1, 4)),
                new TransactionSummaryDTO(1L, BigDecimal.valueOf(105.00), 10, LocalDate.of(2026, 1, 14)),
                new TransactionSummaryDTO(1L, BigDecimal.valueOf(110.00), 40, LocalDate.of(2026, 2, 24)),
                new TransactionSummaryDTO(1L, BigDecimal.valueOf(107.00), 60, LocalDate.of(2026, 3, 23))
        );

        RewardDTO response = new RewardDTO(
                customerId,
                "Jatin Singh",
                dateRange.startDate(),
                dateRange.endDate(),
                monthMap,
                200,
                transactions
        );

        when(transactionService.calculateRewards(eq(customerId), any(DateRange.class)))
                .thenReturn(response);

        mockMvc.perform(get("/api/rewards/{customerId}", customerId)
                        .param("startDate", "2026-01-01")
                        .param("endDate", "2026-03-02")
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1L))
                .andExpect(jsonPath("$.customerName").value("Jatin Singh"))
                .andExpect(jsonPath("$.totalPoints").value(200));
    }

    @Test
    void shouldReturnBadRequestWhenCustomerIdMissing() throws Exception {

        mockMvc.perform(get("/api/rewards/"))
                .andExpect(status().is4xxClientError());
    }

}