package com.example.LogisticsNetworkSystem.dtos;

import com.example.LogisticsNetworkSystem.entities.Carrier;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Carries logistics carrier data between the API layer and application services. */
@Data
@NoArgsConstructor
public class CarrierDTO {

    private Long id;

    @NotBlank(message = "name cannot be blank")
    @Size(max = 255, message = "name cannot exceed 255 characters")
    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "contactEmail cannot be blank")
    @Size(max = 255, message = "contactEmail cannot exceed 255 characters")
    @Email(message = "contactEmail must be a valid email")
    private String contactEmail;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "phoneNumber cannot be blank")
    @Size(max = 255, message = "phoneNumber cannot exceed 255 characters")
    private String phoneNumber;

    @NotBlank(message = "country cannot be blank")
    @Size(max = 255, message = "country cannot exceed 255 characters")
    private String country;

    @Builder
    public CarrierDTO(
            Long id,
            String name,
            String contactEmail,
            String phoneNumber,
            String country) {
        this.id = id;
        this.name = name;
        this.contactEmail = contactEmail;
        this.phoneNumber = phoneNumber;
        this.country = country;
    }

    public static CarrierDTO convertToDTO(Carrier entity) {
        if (entity == null) {
            return null;
        }
        return CarrierDTO.builder()
.id(entity.getId())
                .name(entity.getName())
                .country(entity.getCountry())
                .build();
    }

    public static List<CarrierDTO> convertToDTO(List<Carrier> entities) {
        return entities.stream()
                .map(CarrierDTO::convertToDTO)
                .collect(Collectors.toList());
    }
}
