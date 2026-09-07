package com.example.LogisticsNetworkSystem.dtos;

import com.example.LogisticsNetworkSystem.entities.Shipment;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Carries shipment details, relationships, and nested shipment items. */
@Data
@NoArgsConstructor
public class ShipmentDTO {

    /** Unique identifier of the shipment. */
    private Long id;

    @NotNull(message = "shipmentDate cannot be null")
    @PastOrPresent(message = "shipmentDate cannot be in the future")
    /** Date on which the shipment is scheduled or recorded. */
    private Date shipmentDate;

    @NotBlank(message = "status cannot be blank")
    @Size(max = 255, message = "status cannot exceed 255 characters")
    /** Current lifecycle status of the shipment. */
    private String status;

    @PositiveOrZero(message = "totalWeight cannot be negative")
    /** Total shipment weight represented in the transfer model. */
    private Double totalWeight;

    @NotNull(message = "warehouseId cannot be null")
    @Positive(message = "warehouseId must be greater than zero")
    /** Identifier of the warehouse handling the shipment. */
    private Long warehouseId;

    @NotNull(message = "customerId cannot be null")
    @Positive(message = "customerId must be greater than zero")
    /** Identifier of the customer receiving the shipment. */
    private Long customerId;

    @Positive(message = "carrierId must be greater than zero")
    /** Optional identifier of the carrier assigned to the shipment. */
    private Long carrierId;

    @Valid
    /** Nested item data included with the shipment transfer. */
    private List<ShipmentItemDTO> shipmentItems;

    @Builder
    public ShipmentDTO(
            Long id,
            Date shipmentDate,
            String status,
            Double totalWeight,
            Long warehouseId,
            Long customerId,
            Long carrierId,
            List<ShipmentItemDTO> shipmentItems) {
        this.id = id;
        this.shipmentDate = shipmentDate;
        this.status = status;
        this.totalWeight = totalWeight;
        this.warehouseId = warehouseId;
        this.customerId = customerId;
        this.carrierId = carrierId;
        this.shipmentItems = shipmentItems;
    }

    public static ShipmentDTO convertToDTO(Shipment entity) {
        if (entity == null) {
            return null;
        }
        return ShipmentDTO.builder()
.id(entity.getId())
                .shipmentDate(entity.getShipmentDate())
                .status(entity.getStatus())
                .totalWeight(entity.getTotalWeight())
                .warehouseId(entity.getWarehouse() == null ? null : entity.getWarehouse().getId())
                .customerId(entity.getCustomer() == null ? null : entity.getCustomer().getId())
                .carrierId(entity.getCarrier() == null ? null : entity.getCarrier().getId())
                .shipmentItems(ShipmentItemDTO.convertToDTO(entity.getShipmentItems()))
                .build();
    }

    public static List<ShipmentDTO> convertToDTO(List<Shipment> entities) {
        return entities.stream()
                .map(ShipmentDTO::convertToDTO)
                .collect(Collectors.toList());
    }
}
