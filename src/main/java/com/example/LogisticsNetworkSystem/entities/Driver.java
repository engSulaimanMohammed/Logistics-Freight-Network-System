package com.example.LogisticsNetworkSystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Driver extends BaseClass {
    private String name;
    private String licenseNumber;
    private String phoneNumber;
    private String status;

    @ManyToOne
    private Carrier carrier;

    @JsonIgnore
    @OneToMany(mappedBy = "driver")
    private List<Route> routes = new ArrayList<>();
}
