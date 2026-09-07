package com.example.LogisticsNetworkSystem.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Describes warehouse shipment and inventory metrics returned by the API. */
@Data
@NoArgsConstructor
public class WarehouseStatsDTO {
    /** Identifier of the warehouse represented by these metrics. */
    private Long warehouseId;
    /** Number of active shipments associated with the warehouse. */
    private long activeShipments;
    /** Total inventory units held by the warehouse. */
    private long totalInventoryUnits;

    @Builder
    /** Builds warehouse metrics from values transferred through the API. */
    public WarehouseStatsDTO(
            Long warehouseId,
            long activeShipments,
            long totalInventoryUnits) {
        this.warehouseId = warehouseId;
        this.activeShipments = activeShipments;
        this.totalInventoryUnits = totalInventoryUnits;
    }
}
