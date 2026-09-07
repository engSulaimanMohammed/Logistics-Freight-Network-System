package com.example.LogisticsNetworkSystem.dtos;

import com.example.LogisticsNetworkSystem.entities.Route;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Carries route planning data and its assigned vehicle and driver relationships. */
@Data
@NoArgsConstructor
public class RouteDTO {

    /** Unique identifier of the route. */
    private Long id;

    @NotNull(message = "routeDate cannot be null")
    /** Date and time scheduled for the route. */
    private Date routeDate;

    @NotBlank(message = "origin cannot be blank")
    @Size(max = 255, message = "origin cannot exceed 255 characters")
    /** Starting location of the route. */
    private String origin;

    @NotBlank(message = "destination cannot be blank")
    @Size(max = 255, message = "destination cannot exceed 255 characters")
    /** Ending location of the route. */
    private String destination;

    @NotBlank(message = "status cannot be blank")
    @Size(max = 255, message = "status cannot exceed 255 characters")
    /** Current status of the route. */
    private String status;

    @NotNull(message = "vehicleId cannot be null")
    @Positive(message = "vehicleId must be greater than zero")
    /** Identifier of the vehicle assigned to the route. */
    private Long vehicleId;

    @NotNull(message = "driverId cannot be null")
    @Positive(message = "driverId must be greater than zero")
    /** Identifier of the driver assigned to the route. */
    private Long driverId;

    @Builder
    public RouteDTO(
            Long id,
            Date routeDate,
            String origin,
            String destination,
            String status,
            Long vehicleId,
            Long driverId) {
        this.id = id;
        this.routeDate = routeDate;
        this.origin = origin;
        this.destination = destination;
        this.status = status;
        this.vehicleId = vehicleId;
        this.driverId = driverId;
    }

    public static RouteDTO convertToDTO(Route entity) {
        if (entity == null) {
            return null;
        }
        return RouteDTO.builder()
.id(entity.getId())
                .routeDate(entity.getRouteDate())
                .origin(entity.getOrigin())
                .destination(entity.getDestination())
                .status(entity.getStatus())
                .vehicleId(entity.getVehicle() == null ? null : entity.getVehicle().getId())
                .driverId(entity.getDriver() == null ? null : entity.getDriver().getId())
                .build();
    }

    public static List<RouteDTO> convertToDTO(List<Route> entities) {
        return entities.stream()
                .map(RouteDTO::convertToDTO)
                .collect(Collectors.toList());
    }
}
