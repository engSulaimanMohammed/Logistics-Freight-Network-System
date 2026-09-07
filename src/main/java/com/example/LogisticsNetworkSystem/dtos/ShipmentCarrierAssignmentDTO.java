package com.example.LogisticsNetworkSystem.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Carries the identifiers required to assign a carrier to a shipment. */
@Data
@NoArgsConstructor
public class ShipmentCarrierAssignmentDTO {
    @NotNull(message = "shipmentId cannot be null")
    @Positive(message = "shipmentId must be greater than zero")
    private Long shipmentId;

    @NotNull(message = "carrierId cannot be null")
    @Positive(message = "carrierId must be greater than zero")
    private Long carrierId;

    @Builder
    public ShipmentCarrierAssignmentDTO(Long shipmentId, Long carrierId) {
        this.shipmentId = shipmentId;
        this.carrierId = carrierId;
    }
}
