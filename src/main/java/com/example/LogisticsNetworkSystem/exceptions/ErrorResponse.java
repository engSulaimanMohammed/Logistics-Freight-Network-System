package com.example.LogisticsNetworkSystem.exceptions;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;

public record ErrorResponse(
        int status,
        String error,
        String message,
        LocalDateTime timestamp
) {
    public static ErrorResponse from(HttpStatus status, String message) {
        return new ErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                message,
                LocalDateTime.now()
        );
    }

    public static ErrorResponse from(HttpStatus status, Exception exception) {
        return from(status, exception.getMessage());
    }
}
