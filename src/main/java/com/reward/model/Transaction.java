package com.reward.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table(name = "transactions")
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;
    private LocalDate transactionDate;
    private Long customerId;

    public Transaction() {
    }

    public Transaction(Long transactionId, BigDecimal amount, LocalDate transactionDate, Long customerId) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.customerId = customerId;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
