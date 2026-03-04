package com.reward.service;

import com.reward.dto.DateRange;
import com.reward.dto.RewardDTO;

public interface TransactionService {
    RewardDTO calculateRewards(Long customerId, DateRange dateRange);
}
