package com.example.LogisticsNetworkSystem.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
