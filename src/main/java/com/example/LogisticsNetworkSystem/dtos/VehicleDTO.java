package com.example.LogisticsNetworkSystem.dtos;

import com.example.LogisticsNetworkSystem.entities.Vehicle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Carries vehicle specifications, operating status, and carrier relationship data. */
@Data
@NoArgsConstructor
public class VehicleDTO {

    private Long id;

    @NotBlank(message = "plateNumber cannot be blank")
    @Size(max = 255, message = "plateNumber cannot exceed 255 characters")
    private String plateNumber;

    @NotBlank(message = "type cannot be blank")
    @Size(max = 255, message = "type cannot exceed 255 characters")
    private String type;

    @NotNull(message = "capacityKg cannot be null")
    @Positive(message = "capacityKg must be greater than zero")
    private Double capacityKg;

    @NotBlank(message = "status cannot be blank")
    @Size(max = 255, message = "status cannot exceed 255 characters")
    private String status;

    @NotNull(message = "carrierId cannot be null")
    @Positive(message = "carrierId must be greater than zero")
    private Long carrierId;

    @Builder
    public VehicleDTO(
            Long id,
            String plateNumber,
            String type,
            Double capacityKg,
            String status,
            Long carrierId) {
        this.id = id;
        this.plateNumber = plateNumber;
        this.type = type;
        this.capacityKg = capacityKg;
        this.status = status;
        this.carrierId = carrierId;
    }

    public static VehicleDTO convertToDTO(Vehicle entity) {
        if (entity == null) {
            return null;
        }
        return VehicleDTO.builder()
.id(entity.getId())
                .plateNumber(entity.getPlateNumber())
                .type(entity.getType())
                .capacityKg(entity.getCapacityKg())
                .status(entity.getStatus())
                .carrierId(entity.getCarrier() == null ? null : entity.getCarrier().getId())
                .build();
    }

    public static List<VehicleDTO> convertToDTO(List<Vehicle> entities) {
        return entities.stream()
                .map(VehicleDTO::convertToDTO)
                .collect(Collectors.toList());
    }
}
