package com.example.LogisticsNetworkSystem.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ShipmentItem extends BaseClass {
    private Integer quantity;

    @ManyToOne
    private Shipment shipment;

    @ManyToOne
    private Product product;
}
