package com.example.LogisticsNetworkSystem.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product extends BaseClass {
    private String name;
    private String sku;
    private Double weightKg;
    private String category;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<InventoryItem> inventoryItems = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<ShipmentItem> shipmentItems = new ArrayList<>();
}
