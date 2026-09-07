package com.example.LogisticsNetworkSystem.dtos;

import com.example.LogisticsNetworkSystem.entities.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Carries customer address data between the API layer and application services. */
@Data
@NoArgsConstructor
public class AddressDTO {

    /** Unique identifier of the address. */
    private Long id;

    @NotBlank(message = "street cannot be blank")
    @Size(max = 255, message = "street cannot exceed 255 characters")
    /** Street name and number for the address. */
    private String street;

    @NotBlank(message = "city cannot be blank")
    @Size(max = 255, message = "city cannot exceed 255 characters")
    /** City in which the address is located. */
    private String city;

    @NotBlank(message = "postalCode cannot be blank")
    @Size(max = 255, message = "postalCode cannot exceed 255 characters")
    /** Postal code associated with the address. */
    private String postalCode;

    @NotBlank(message = "country cannot be blank")
    @Size(max = 255, message = "country cannot exceed 255 characters")
    /** Country in which the address is located. */
    private String country;

    @NotNull(message = "customerId cannot be null")
    @Positive(message = "customerId must be greater than zero")
    /** Identifier of the customer associated with the address. */
    private Long customerId;

    /** Builds a AddressDTO from values transferred through the API. */
    @Builder
    public AddressDTO(
            Long id,
            String street,
            String city,
            String postalCode,
            String country,
            Long customerId) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
        this.customerId = customerId;
    }

    /** Converts an Address entity into its API transfer representation. */
    public static AddressDTO convertToDTO(Address entity) {
        if (entity == null) {
            return null;
        }
        return AddressDTO.builder()
.id(entity.getId())
                .street(entity.getStreet())
                .city(entity.getCity())
                .postalCode(entity.getPostalCode())
                .country(entity.getCountry())
                .customerId(entity.getCustomer() == null ? null : entity.getCustomer().getId())
                .build();
    }

    /** Converts a collection of Address entities into response DTOs. */
    public static List<AddressDTO> convertToDTO(List<Address> entities) {
        return entities.stream()
                .map(AddressDTO::convertToDTO)
                .collect(Collectors.toList());
    }
}
