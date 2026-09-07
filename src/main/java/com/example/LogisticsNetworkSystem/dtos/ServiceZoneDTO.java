package com.example.LogisticsNetworkSystem.dtos;

import com.example.LogisticsNetworkSystem.entities.ServiceZone;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Carries service zone configuration data between API and application layers. */
@Data
@NoArgsConstructor
public class ServiceZoneDTO {

    private Long id;

    @NotBlank(message = "name cannot be blank")
    @Size(max = 255, message = "name cannot exceed 255 characters")
    private String name;

    @NotBlank(message = "region cannot be blank")
    @Size(max = 255, message = "region cannot exceed 255 characters")
    private String region;

    @NotNull(message = "baseRate cannot be null")
    @Positive(message = "baseRate must be greater than zero")
    private Double baseRate;

    @Builder
    public ServiceZoneDTO(
            Long id,
            String name,
            String region,
            Double baseRate) {
        this.id = id;
        this.name = name;
        this.region = region;
        this.baseRate = baseRate;
    }

    public static ServiceZoneDTO convertToDTO(ServiceZone entity) {
        if (entity == null) {
            return null;
        }
        return ServiceZoneDTO.builder()
.id(entity.getId())
                .name(entity.getName())
                .region(entity.getRegion())
                .baseRate(entity.getBaseRate())
                .build();
    }

    public static List<ServiceZoneDTO> convertToDTO(List<ServiceZone> entities) {
        return entities.stream()
                .map(ServiceZoneDTO::convertToDTO)
                .collect(Collectors.toList());
    }
}
