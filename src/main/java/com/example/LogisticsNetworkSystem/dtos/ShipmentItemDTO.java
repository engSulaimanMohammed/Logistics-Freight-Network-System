package com.example.LogisticsNetworkSystem.dtos;

import com.example.LogisticsNetworkSystem.entities.ShipmentItem;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Carries product quantity data associated with a shipment. */
@Data
@NoArgsConstructor
public class ShipmentItemDTO {

    private Long id;

    @NotNull(message = "quantity cannot be null")
    @Positive(message = "quantity must be greater than zero")
    private Integer quantity;

    @Positive(message = "shipmentId must be greater than zero")
    private Long shipmentId;

    @NotNull(message = "productId cannot be null")
    @Positive(message = "productId must be greater than zero")
    private Long productId;

    @Builder
    public ShipmentItemDTO(
            Long id,
            Integer quantity,
            Long shipmentId,
            Long productId) {
        this.id = id;
        this.quantity = quantity;
        this.shipmentId = shipmentId;
        this.productId = productId;
    }

    public static ShipmentItemDTO convertToDTO(ShipmentItem entity) {
        if (entity == null) {
            return null;
        }
        return ShipmentItemDTO.builder()
.id(entity.getId())
                .quantity(entity.getQuantity())
                .shipmentId(entity.getShipment() == null ? null : entity.getShipment().getId())
                .productId(entity.getProduct() == null ? null : entity.getProduct().getId())
                .build();
    }

    public static List<ShipmentItemDTO> convertToDTO(List<ShipmentItem> entities) {
        return entities.stream()
                .map(ShipmentItemDTO::convertToDTO)
                .collect(Collectors.toList());
    }
}
