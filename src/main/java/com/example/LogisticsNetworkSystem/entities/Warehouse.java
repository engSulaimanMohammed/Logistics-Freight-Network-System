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
public class Warehouse extends BaseClass {
    private String name;
    private String location;
    private Integer capacity;

    @OneToMany(mappedBy = "warehouse")
    @JsonIgnore
    private List<InventoryItem> inventoryItems = new ArrayList<>();

    @OneToMany(mappedBy = "warehouse")
    @JsonIgnore
    private List<Shipment> shipments = new ArrayList<>();

    @OneToMany(mappedBy = "warehouse")
    @JsonIgnore
    private List<Staff> staff = new ArrayList<>();
}
