package com.example.LogisticsNetworkSystem.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class InventoryItem extends BaseClass {
    private Integer quantity;
    private String shelfLocation;

    @ManyToOne
    private Warehouse warehouse;

    @ManyToOne
    private Product product;
}
