package com.reward.service.impl;

import com.reward.dto.DateRange;
import com.reward.dto.RewardDTO;
import com.reward.dto.TransactionSummaryDTO;
import com.reward.exception.NoTransactionFoundException;
import com.reward.exception.UserNotFoundException;
import com.reward.model.Customer;
import com.reward.model.Transaction;
import com.reward.repository.CustomerRepository;
import com.reward.repository.TransactionRepository;
import com.reward.service.TransactionService;
import com.reward.utils.RewardCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final CustomerRepository customerRepository;
    private static final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  CustomerRepository customerRepository) {
        this.transactionRepository = transactionRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public RewardDTO calculateRewards(Long customerId, DateRange dateRange) {

        log.debug("Checking if customer exists with id={}", customerId);
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "Customer not found with id: " + customerId
                        )
                );

        List<Transaction> transactionList =
                transactionRepository.findByCustomerIdAndTransactionDateBetween(
                        customerId,
                        dateRange.startDate(),
                        dateRange.endDate()
                );

        if (transactionList.isEmpty()) {
            log.debug("No transactions found for customerId={} between {} and {}",
                    customerId,
                    dateRange.startDate(),
                    dateRange.endDate());

            throw new NoTransactionFoundException("No transaction found for customerId: " + customerId);
        }

        log.debug("Fetched {} transactions for customerId={} between {} and {}",
                transactionList.size(),
                customerId,
                dateRange.startDate(),
                dateRange.endDate());

        Map<YearMonth, Integer> monthlyPoints =
                transactionList.stream()
                        .collect(Collectors.groupingBy(
                                t -> YearMonth.from(t.getTransactionDate()),
                                Collectors.summingInt(
                                        t -> RewardCalculator.calculate(t.getAmount())
                                )
                        ));

        int totalPoints =
                monthlyPoints.values().stream()
                        .mapToInt(Integer::intValue)
                        .sum();

        List<TransactionSummaryDTO> transactionSummaryListDto =
                transactionList.stream()
                        .map(t -> new TransactionSummaryDTO(
                                t.getTransactionId(),
                                t.getAmount(),
                                RewardCalculator.calculate(t.getAmount()),
                                t.getTransactionDate()
                        ))
                        .toList();

        log.info("Reward calculation completed for customerId={}, totalPoints={}", customerId, totalPoints);
        return new RewardDTO(
                customer.getCustomerId(),
                customer.getName(),
                dateRange.startDate(),
                dateRange.endDate(),
                monthlyPoints,
                totalPoints,
                transactionSummaryListDto
        );
    }
}
