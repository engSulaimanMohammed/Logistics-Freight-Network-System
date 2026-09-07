package com.example.LogisticsNetworkSystem.dtos;

import com.example.LogisticsNetworkSystem.entities.DeliveryStop;
import jakarta.validation.constraints.Future;
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

/** Carries delivery stop details and route or shipment relationships. */
@Data
@NoArgsConstructor
public class DeliveryStopDTO {

    private Long id;

    @NotNull(message = "sequence cannot be null")
    @Positive(message = "sequence must be greater than zero")
    private Integer sequence;

    @NotBlank(message = "address cannot be blank")
    @Size(max = 255, message = "address cannot exceed 255 characters")
    private String address;

    @NotBlank(message = "status cannot be blank")
    @Size(max = 255, message = "status cannot exceed 255 characters")
    private String status;

    @NotNull(message = "eta cannot be null")
    @Future(message = "eta must be in the future")
    private Date eta;

    @NotNull(message = "routeId cannot be null")
    @Positive(message = "routeId must be greater than zero")
    private Long routeId;

    @NotNull(message = "shipmentId cannot be null")
    @Positive(message = "shipmentId must be greater than zero")
    private Long shipmentId;

    @Builder
    public DeliveryStopDTO(
            Long id,
            Integer sequence,
            String address,
            String status,
            Date eta,
            Long routeId,
            Long shipmentId) {
        this.id = id;
        this.sequence = sequence;
        this.address = address;
        this.status = status;
        this.eta = eta;
        this.routeId = routeId;
        this.shipmentId = shipmentId;
    }

    public static DeliveryStopDTO convertToDTO(DeliveryStop entity) {
        if (entity == null) {
            return null;
        }
        return DeliveryStopDTO.builder()
.id(entity.getId())
                .sequence(entity.getSequence())
                .address(entity.getAddress())
                .status(entity.getStatus())
                .eta(entity.getEta())
                .routeId(entity.getRoute() == null ? null : entity.getRoute().getId())
                .shipmentId(entity.getShipment() == null ? null : entity.getShipment().getId())
                .build();
    }

    public static List<DeliveryStopDTO> convertToDTO(List<DeliveryStop> entities) {
        return entities.stream()
                .map(DeliveryStopDTO::convertToDTO)
                .collect(Collectors.toList());
    }
}
