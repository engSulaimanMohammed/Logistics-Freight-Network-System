package com.example.LogisticsNetworkSystem.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Shipment extends BaseClass {
    private Date shipmentDate;
    private String status;
    private Double totalWeight;

    @ManyToOne
    private Warehouse warehouse;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Carrier carrier;

    @JsonIgnore
    @OneToMany(mappedBy = "shipment")
    private List<ShipmentItem> shipmentItems = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "shipment")
    private List<TrackingEvent> trackingEvents = new ArrayList<>();
}
