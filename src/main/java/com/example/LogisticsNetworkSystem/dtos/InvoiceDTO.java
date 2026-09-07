package com.example.LogisticsNetworkSystem.dtos;

import com.example.LogisticsNetworkSystem.entities.Invoice;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InvoiceDTO {

    private Long id;

    @NotNull(message = "amount cannot be null")
    @Positive(message = "amount must be greater than zero")
    private Double amount;

    @NotBlank(message = "status cannot be blank")
    @Size(max = 255, message = "status cannot exceed 255 characters")
    private String status;

    @NotNull(message = "issuedDate cannot be null")
    @PastOrPresent(message = "issuedDate cannot be in the future")
    private Date issuedDate;

    @NotNull(message = "shipmentId cannot be null")
    @Positive(message = "shipmentId must be greater than zero")
    private Long shipmentId;

    @Positive(message = "customerId must be greater than zero")
    private Long customerId;

    @Builder
    public InvoiceDTO(
            Long id,
            Double amount,
            String status,
            Date issuedDate,
            Long shipmentId,
            Long customerId) {
        this.id = id;
        this.amount = amount;
        this.status = status;
        this.issuedDate = issuedDate;
        this.shipmentId = shipmentId;
        this.customerId = customerId;
    }

    public static InvoiceDTO convertToDTO(Invoice entity) {
        if (entity == null) {
            return null;
        }
        return InvoiceDTO.builder()
.id(entity.getId())
                .amount(entity.getAmount())
                .status(entity.getStatus())
                .issuedDate(entity.getIssuedDate())
                .shipmentId(entity.getShipment() == null ? null : entity.getShipment().getId())
                .customerId(entity.getCustomer() == null ? null : entity.getCustomer().getId())
                .build();
    }

    public static List<InvoiceDTO> convertToDTO(List<Invoice> entities) {
        return entities.stream()
                .map(InvoiceDTO::convertToDTO)
                .collect(Collectors.toList());
    }
}
