package com.example.LogisticsNetworkSystem.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Describes carrier-level operational metrics returned by the API. */
@Data
@NoArgsConstructor
public class CarrierStatsDTO {
    private Long carrierId;
    private long vehicles;
    private long drivers;
    private long activeRoutes;

    @Builder
    public CarrierStatsDTO(Long carrierId, long vehicles, long drivers, long activeRoutes) {
        this.carrierId = carrierId;
        this.vehicles = vehicles;
        this.drivers = drivers;
        this.activeRoutes = activeRoutes;
    }
}
