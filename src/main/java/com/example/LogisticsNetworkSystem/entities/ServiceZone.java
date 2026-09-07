package com.example.LogisticsNetworkSystem.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ServiceZone extends BaseClass {
    private String name;
    private String region;
    private Double baseRate;

    @OneToMany
    @JoinColumn(name = "service_zone_id")
    @JsonIgnore
    private List<Address> addresses = new ArrayList<>();
}
