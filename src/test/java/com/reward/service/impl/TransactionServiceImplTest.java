package com.reward.service.impl;

import com.reward.dto.DateRange;
import com.reward.dto.RewardDTO;
import com.reward.exception.NoTransactionFoundException;
import com.reward.exception.UserNotFoundException;
import com.reward.model.Customer;
import com.reward.model.Transaction;
import com.reward.repository.CustomerRepository;
import com.reward.repository.TransactionRepository;
import com.reward.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private DateRange dateRange;

    @BeforeEach
    void setup() {
        dateRange = new DateRange(
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 3, 1)
        );
    }

    @Test
    void shouldCalculateRewardSuccessfully() {
        Long customerId = 1L;
        Customer customer = new Customer(1L, "Jatin Pratap Singh");

        Transaction t1 = new Transaction(1L, BigDecimal.valueOf(120.00),
                LocalDate.of(2026, 1, 10), 1L);

        Transaction t2 = new Transaction(2L, BigDecimal.valueOf(75.0),
                LocalDate.of(2026, 2, 10), 1L);

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(transactionRepository.findByCustomerIdAndTransactionDateBetween(customerId, dateRange.startDate(), dateRange.endDate()))
                .thenReturn(List.of(t1, t2));

        RewardDTO result = transactionService.calculateRewards(customerId, dateRange);

        assertNotNull(result);
        assertEquals(1L, result.customerId());
        assertEquals("Jatin Pratap Singh", result.customerName());
        assertTrue(result.totalPoints() > 0);
    }

    @Test
    void shouldThrowExceptionWhenCustomerNotFound() {
        Long customerId = 5L;
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> transactionService.calculateRewards(customerId, dateRange));
    }

    @Test
    void shouldThrowExceptionWhenWhenNoTransactionsFound() {
        Long customerId = 1L;
        Customer customer = new Customer(1L, "Jatin Singh");

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        assertThrows(NoTransactionFoundException.class, () -> transactionService.calculateRewards(customerId, dateRange));
    }

}