package com.example.LogisticsNetworkSystem.dtos;

import com.example.LogisticsNetworkSystem.entities.Customer;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Carries customer data between the API layer and application services. */
@Data
@NoArgsConstructor
public class CustomerDTO {

    /** Unique identifier of the customer. */
    private Long id;

    @NotBlank(message = "name cannot be blank")
    @Size(max = 255, message = "name cannot exceed 255 characters")
    /** Customer name presented through the API. */
    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "email cannot be blank")
    @Size(max = 255, message = "email cannot exceed 255 characters")
    @Email(message = "email must be a valid email")
    /** Email address used for customer communication. */
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "phoneNumber cannot be blank")
    @Size(max = 255, message = "phoneNumber cannot exceed 255 characters")
    /** Phone number used for customer communication. */
    private String phoneNumber;

    @NotBlank(message = "type cannot be blank")
    @Size(max = 255, message = "type cannot exceed 255 characters")
    /** Customer classification supplied by the API. */
    private String type;

    @Builder
    public CustomerDTO(
            Long id,
            String name,
            String email,
            String phoneNumber,
            String type) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.type = type;
    }

    public static CustomerDTO convertToDTO(Customer entity) {
        if (entity == null) {
            return null;
        }
        return CustomerDTO.builder()
.id(entity.getId())
                .name(entity.getName())
                .type(entity.getType())
                .build();
    }

    public static List<CustomerDTO> convertToDTO(List<Customer> entities) {
        return entities.stream()
                .map(CustomerDTO::convertToDTO)
                .collect(Collectors.toList());
    }
}
