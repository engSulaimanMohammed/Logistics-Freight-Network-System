package com.example.LogisticsNetworkSystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Route extends BaseClass {
    private Date routeDate;
    private String origin;
    private String destination;
    private String status;

    @ManyToOne
    private Vehicle vehicle;

    @ManyToOne
    private Driver driver;

    @JsonIgnore
    @OneToMany(mappedBy = "route")
    private List<DeliveryStop> deliveryStops = new ArrayList<>();
}
