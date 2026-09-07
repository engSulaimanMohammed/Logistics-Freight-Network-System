package com.example.LogisticsNetworkSystem.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Carries the identifier required to complete a delivery stop. */
@Data
@NoArgsConstructor
public class DeliveryStopCompleteDTO {
    @NotNull(message = "deliveryStopId cannot be null")
    @Positive(message = "deliveryStopId must be greater than zero")
    /** Identifier of the delivery stop to mark as complete. */
    private Long deliveryStopId;

    @Builder
    /** Builds a completion request from the target stop identifier. */
    public DeliveryStopCompleteDTO(Long deliveryStopId) {
        this.deliveryStopId = deliveryStopId;
    }
}
