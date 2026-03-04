package com.reward.repository;

import com.reward.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findByCustomerIdAndTransactionDateBetween(
            Long customerId,
            LocalDate startDate,
            LocalDate endDate
    );
}
