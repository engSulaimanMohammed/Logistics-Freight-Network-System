package com.example.LogisticsNetworkSystem.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
public class RouteBuildDTO {
    @NotNull(message = "shipmentId cannot be null")
    @Positive(message = "shipmentId must be greater than zero")
    private Long shipmentId;
    @NotNull(message = "routeDate cannot be null")
    private Date routeDate;
    @NotBlank(message = "origin cannot be blank")
    @Size(max = 255, message = "origin cannot exceed 255 characters")
    private String origin;
    @NotBlank(message = "destination cannot be blank")
    @Size(max = 255, message = "destination cannot exceed 255 characters")
    private String destination;
    @NotBlank(message = "status cannot be blank")
    @Size(max = 255, message = "status cannot exceed 255 characters")
    private String status;
    @NotNull(message = "vehicleId cannot be null")
    @Positive(message = "vehicleId must be greater than zero")
    private Long vehicleId;
    @NotNull(message = "driverId cannot be null")
    @Positive(message = "driverId must be greater than zero")
    private Long driverId;

    @Builder
    public RouteBuildDTO(Long shipmentId, Date routeDate, String origin, String destination, String status, Long vehicleId, Long driverId) {
        this.shipmentId = shipmentId;
        this.routeDate = routeDate;
        this.origin = origin;
        this.destination = destination;
        this.status = status;
        this.vehicleId = vehicleId;
        this.driverId = driverId;
    }
}
