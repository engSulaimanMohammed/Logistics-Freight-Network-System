package com.example.LogisticsNetworkSystem.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Describes carrier-level operational metrics returned by the API. */
@Data
@NoArgsConstructor
public class CarrierStatsDTO {
    /** Identifier of the carrier represented by these metrics. */
    private Long carrierId;
    /** Number of vehicles associated with the carrier. */
    private long vehicles;
    /** Number of drivers associated with the carrier. */
    private long drivers;
    /** Number of active routes associated with the carrier. */
    private long activeRoutes;

    @Builder
    /** Builds carrier metrics from values transferred through the API. */
    public CarrierStatsDTO(Long carrierId, long vehicles, long drivers, long activeRoutes) {
        this.carrierId = carrierId;
        this.vehicles = vehicles;
        this.drivers = drivers;
        this.activeRoutes = activeRoutes;
    }
}
