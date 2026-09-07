package com.example.LogisticsNetworkSystem.dtos;

import com.example.LogisticsNetworkSystem.entities.Driver;
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

/** Carries driver information and the carrier relationship used by the API. */
@Data
@NoArgsConstructor
public class DriverDTO {

    /** Unique identifier of the driver. */
    private Long id;

    @NotBlank(message = "name cannot be blank")
    @Size(max = 255, message = "name cannot exceed 255 characters")
    /** Driver name presented through the API. */
    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "licenseNumber cannot be blank")
    @Size(max = 255, message = "licenseNumber cannot exceed 255 characters")
    /** Driver license number supplied for operational records. */
    private String licenseNumber;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "phoneNumber cannot be blank")
    @Size(max = 255, message = "phoneNumber cannot exceed 255 characters")
    /** Phone number used to contact the driver. */
    private String phoneNumber;

    @NotBlank(message = "status cannot be blank")
    @Size(max = 255, message = "status cannot exceed 255 characters")
    /** Current operational status of the driver. */
    private String status;

    @NotNull(message = "carrierId cannot be null")
    @Positive(message = "carrierId must be greater than zero")
    /** Identifier of the carrier employing or managing the driver. */
    private Long carrierId;

    /** Builds a DriverDTO from values transferred through the API. */
    @Builder
    public DriverDTO(
            Long id,
            String name,
            String licenseNumber,
            String phoneNumber,
            String status,
            Long carrierId) {
        this.id = id;
        this.name = name;
        this.licenseNumber = licenseNumber;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.carrierId = carrierId;
    }

    /** Converts a Driver entity into its API transfer representation. */
    public static DriverDTO convertToDTO(Driver entity) {
        if (entity == null) {
            return null;
        }
        return DriverDTO.builder()
.id(entity.getId())
                .name(entity.getName())
                .status(entity.getStatus())
                .carrierId(entity.getCarrier() == null ? null : entity.getCarrier().getId())
                .build();
    }

    /** Converts a collection of Driver entities into response DTOs. */
    public static List<DriverDTO> convertToDTO(List<Driver> entities) {
        return entities.stream()
                .map(DriverDTO::convertToDTO)
                .collect(Collectors.toList());
    }
}
