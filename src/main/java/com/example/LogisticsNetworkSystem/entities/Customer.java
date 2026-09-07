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

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<Shipment> shipments = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "customer")
    private List<Address> addresses = new ArrayList<>();
}
