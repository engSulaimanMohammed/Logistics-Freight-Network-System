package com.example.LogisticsNetworkSystem.dtos;

import com.example.LogisticsNetworkSystem.entities.Staff;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StaffDTO {

    private Long id;

    @NotBlank(message = "name cannot be blank")
    @Size(max = 255, message = "name cannot exceed 255 characters")
    private String name;

    @NotBlank(message = "role cannot be blank")
    @Size(max = 255, message = "role cannot exceed 255 characters")
    private String role;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "phoneNumber cannot be blank")
    @Size(max = 255, message = "phoneNumber cannot exceed 255 characters")
    private String phoneNumber;

    @NotNull(message = "warehouseId cannot be null")
    @Positive(message = "warehouseId must be greater than zero")
    private Long warehouseId;

    @Builder
    public StaffDTO(
            Long id,
            String name,
            String role,
            String phoneNumber,
            Long warehouseId) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.warehouseId = warehouseId;
    }

    public static StaffDTO convertToDTO(Staff entity) {
        if (entity == null) {
            return null;
        }
        return StaffDTO.builder()
.id(entity.getId())
                .name(entity.getName())
                .role(entity.getRole())
                .warehouseId(entity.getWarehouse() == null ? null : entity.getWarehouse().getId())
                .build();
    }

    public static List<StaffDTO> convertToDTO(List<Staff> entities) {
        return entities.stream()
                .map(StaffDTO::convertToDTO)
                .collect(Collectors.toList());
    }
}
