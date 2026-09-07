package com.example.LogisticsNetworkSystem.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerStatsDTO {
    private Long customerId;
    private double totalInvoicedAmount;

    @Builder
    public CustomerStatsDTO(Long customerId, double totalInvoicedAmount) {
        this.customerId = customerId;
        this.totalInvoicedAmount = totalInvoicedAmount;
    }
}
