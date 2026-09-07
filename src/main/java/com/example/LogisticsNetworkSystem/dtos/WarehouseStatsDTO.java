package com.example.LogisticsNetworkSystem.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Describes warehouse shipment and inventory metrics returned by the API. */
@Data
@NoArgsConstructor
public class WarehouseStatsDTO {
    private Long warehouseId;
    private long activeShipments;
    private long totalInventoryUnits;

    @Builder
    public WarehouseStatsDTO(Long warehouseId, long activeShipments, long totalInventoryUnits) {
        this.warehouseId = warehouseId;
        this.activeShipments = activeShipments;
        this.totalInventoryUnits = totalInventoryUnits;
    }
}
