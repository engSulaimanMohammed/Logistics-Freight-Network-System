package com.example.LogisticsNetworkSystem.dtos;

import com.example.LogisticsNetworkSystem.entities.Warehouse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Carries warehouse identity, location, and capacity data. */
@Data
@NoArgsConstructor
public class WarehouseDTO {

    private Long id;

    @NotBlank(message = "name cannot be blank")
    @Size(max = 255, message = "name cannot exceed 255 characters")
    private String name;

    @NotBlank(message = "location cannot be blank")
    @Size(max = 255, message = "location cannot exceed 255 characters")
    private String location;

    @NotNull(message = "capacity cannot be null")
    @Positive(message = "capacity must be greater than zero")
    private Integer capacity;

    @Builder
    public WarehouseDTO(
            Long id,
            String name,
            String location,
            Integer capacity) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.capacity = capacity;
    }

    public static WarehouseDTO convertToDTO(Warehouse entity) {
        if (entity == null) {
            return null;
        }
        return WarehouseDTO.builder()
.id(entity.getId())
                .name(entity.getName())
                .location(entity.getLocation())
                .capacity(entity.getCapacity())
                .build();
    }

    public static List<WarehouseDTO> convertToDTO(List<Warehouse> entities) {
        return entities.stream()
                .map(WarehouseDTO::convertToDTO)
                .collect(Collectors.toList());
    }
}
