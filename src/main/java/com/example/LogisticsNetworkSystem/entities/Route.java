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
public class Route extends BaseClass {
    private Date routeDate;
    private String origin;
    private String destination;
    private String status;

    @ManyToOne
    private Vehicle vehicle;

    @ManyToOne
    private Driver driver;

    @OneToMany(mappedBy = "route")
    @JsonIgnore
    private List<DeliveryStop> deliveryStops = new ArrayList<>();
}
