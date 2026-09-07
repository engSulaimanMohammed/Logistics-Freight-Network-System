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
