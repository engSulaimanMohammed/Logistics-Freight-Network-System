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
public class Carrier extends BaseClass {
    private String name;
    private String contactEmail;
    private String phoneNumber;
    private String country;

    @JsonIgnore
    @OneToMany(mappedBy = "carrier")
    private List<Shipment> shipments = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "carrier")
    private List<Vehicle> vehicles = new ArrayList<>();
}
