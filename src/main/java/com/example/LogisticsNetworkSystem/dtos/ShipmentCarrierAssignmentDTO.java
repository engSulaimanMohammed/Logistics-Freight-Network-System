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
    /** Identifier of the shipment receiving the carrier assignment. */
    private Long shipmentId;

    @NotNull(message = "carrierId cannot be null")
    @Positive(message = "carrierId must be greater than zero")
    /** Identifier of the carrier assigned to the shipment. */
    private Long carrierId;

    @Builder
    /** Builds a carrier assignment request from shipment and carrier identifiers. */
    public ShipmentCarrierAssignmentDTO(Long shipmentId, Long carrierId) {
        this.shipmentId = shipmentId;
        this.carrierId = carrierId;
    }
}
