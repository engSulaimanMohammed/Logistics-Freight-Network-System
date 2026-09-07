package com.example.LogisticsNetworkSystem.entities;

import java.util.ArrayList;
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
public class Vehicle extends BaseClass {
    private String plateNumber;
    private String type;
    private Double capacityKg;
    private String status;

    @ManyToOne
    private Carrier carrier;

    @OneToMany(mappedBy = "vehicle")
    @JsonIgnore
    private List<Route> routes = new ArrayList<>();
}
