package com.reward.controller;

import com.reward.dto.DateRange;
import com.reward.dto.RewardDTO;
import com.reward.service.TransactionService;
import com.reward.utils.DateRangeResolver;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/rewards")
@Validated
public class RewardController {

    private final TransactionService transactionService;
    private static final Logger log = LoggerFactory.getLogger(RewardController.class);

    public RewardController(TransactionService rewardTransactionService) {
        this.transactionService = rewardTransactionService;
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<RewardDTO> getReward(
            @PathVariable
            @NotNull
            Long customerId,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate,

            @RequestParam(required = false)
            Integer noOfMonths
    ) {

        DateRange dateRange = DateRangeResolver.resolve(noOfMonths, startDate, endDate);

        log.info("Fetching rewards for customerId={} from {} to {}",
                customerId, dateRange.startDate(), dateRange.endDate());
        RewardDTO response = transactionService.calculateRewards(customerId, dateRange);

        return ResponseEntity.ok(response);

    }
}
