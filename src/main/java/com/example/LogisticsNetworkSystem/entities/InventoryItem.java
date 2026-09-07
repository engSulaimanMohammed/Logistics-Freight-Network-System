package com.example.LogisticsNetworkSystem.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class InventoryItem extends BaseClass {
    private Integer quantity;
    private String shelfLocation;

    @ManyToOne
    private Warehouse warehouse;

    @ManyToOne
    private Product product;
}
