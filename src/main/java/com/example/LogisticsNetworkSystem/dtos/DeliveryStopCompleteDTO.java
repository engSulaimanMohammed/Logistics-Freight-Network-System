package com.example.LogisticsNetworkSystem.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeliveryStopCompleteDTO {
    @NotNull(message = "deliveryStopId cannot be null")
    @Positive(message = "deliveryStopId must be greater than zero")
    private Long deliveryStopId;

    @Builder
    public DeliveryStopCompleteDTO(Long deliveryStopId) {
        this.deliveryStopId = deliveryStopId;
    }
}
