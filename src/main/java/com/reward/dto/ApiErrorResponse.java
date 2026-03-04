package com.reward.dto;

import java.time.LocalDateTime;

public record ApiErrorResponse(
        LocalDateTime timestamp,
        String error,
        int status,
        String path
) {
}
