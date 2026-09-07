package com.example.LogisticsNetworkSystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Vehicle extends BaseClass {
    private String plateNumber;
    private String type;
    private Double capacityKg;
    private String status;

    @ManyToOne
    private Carrier carrier;

    @JsonIgnore
    @OneToMany(mappedBy = "vehicle")
    private List<Route> routes = new ArrayList<>();
}
