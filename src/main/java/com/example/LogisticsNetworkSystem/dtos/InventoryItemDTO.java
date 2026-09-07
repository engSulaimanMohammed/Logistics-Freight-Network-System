package com.example.LogisticsNetworkSystem.dtos;

import com.example.LogisticsNetworkSystem.entities.InventoryItem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InventoryItemDTO {

    private Long id;

    @NotNull(message = "quantity cannot be null")
    @PositiveOrZero(message = "quantity cannot be negative")
    private Integer quantity;

    @NotBlank(message = "shelfLocation cannot be blank")
    @Size(max = 255, message = "shelfLocation cannot exceed 255 characters")
    private String shelfLocation;

    @NotNull(message = "warehouseId cannot be null")
    @Positive(message = "warehouseId must be greater than zero")
    private Long warehouseId;

    @NotNull(message = "productId cannot be null")
    @Positive(message = "productId must be greater than zero")
    private Long productId;

    @Builder
    public InventoryItemDTO(
            Long id,
            Integer quantity,
            String shelfLocation,
            Long warehouseId,
            Long productId) {
        this.id = id;
        this.quantity = quantity;
        this.shelfLocation = shelfLocation;
        this.warehouseId = warehouseId;
        this.productId = productId;
    }

    public static InventoryItemDTO convertToDTO(InventoryItem entity) {
        if (entity == null) {
            return null;
        }
        return InventoryItemDTO.builder()
.id(entity.getId())
                .quantity(entity.getQuantity())
                .shelfLocation(entity.getShelfLocation())
                .warehouseId(entity.getWarehouse() == null ? null : entity.getWarehouse().getId())
                .productId(entity.getProduct() == null ? null : entity.getProduct().getId())
                .build();
    }

    public static List<InventoryItemDTO> convertToDTO(List<InventoryItem> entities) {
        return entities.stream()
                .map(InventoryItemDTO::convertToDTO)
                .collect(Collectors.toList());
    }
}
