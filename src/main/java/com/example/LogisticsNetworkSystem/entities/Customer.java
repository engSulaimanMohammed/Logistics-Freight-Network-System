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
public class Customer extends BaseClass {
    private String name;
    private String email;
    private String phoneNumber;
    private String type;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<Shipment> shipments = new ArrayList<>();

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<Address> addresses = new ArrayList<>();
}
