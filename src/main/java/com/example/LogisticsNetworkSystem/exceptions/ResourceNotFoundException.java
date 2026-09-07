package com.example.LogisticsNetworkSystem.exceptions;

import java.io.Serial;

public class ResourceNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(
            String message,
            Throwable cause
    ) {
        super(message, cause);
    }

    public ResourceNotFoundException(String resourceName, Object resourceId) {
        this(resourceName + " not found with id: " + resourceId);
    }
}
