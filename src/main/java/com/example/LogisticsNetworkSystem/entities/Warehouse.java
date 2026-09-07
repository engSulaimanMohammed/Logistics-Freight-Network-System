package com.example.LogisticsNetworkSystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Warehouse extends BaseClass {
    private String name;
    private String location;
    private Integer capacity;

    @JsonIgnore
    @OneToMany(mappedBy = "warehouse")
    private List<InventoryItem> inventoryItems = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "warehouse")
    private List<Shipment> shipments = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "warehouse")
    private List<Staff> staff = new ArrayList<>();
}
