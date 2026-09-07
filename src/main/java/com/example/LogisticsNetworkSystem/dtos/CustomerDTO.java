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

@Data
@NoArgsConstructor
public class CustomerDTO {

    private Long id;

    @NotBlank(message = "name cannot be blank")
    @Size(max = 255, message = "name cannot exceed 255 characters")
    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "email cannot be blank")
    @Size(max = 255, message = "email cannot exceed 255 characters")
    @Email(message = "email must be a valid email")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "phoneNumber cannot be blank")
    @Size(max = 255, message = "phoneNumber cannot exceed 255 characters")
    private String phoneNumber;

    @NotBlank(message = "type cannot be blank")
    @Size(max = 255, message = "type cannot exceed 255 characters")
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
