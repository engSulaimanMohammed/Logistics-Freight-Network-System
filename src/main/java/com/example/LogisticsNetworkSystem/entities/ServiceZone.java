package com.example.LogisticsNetworkSystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class ServiceZone extends BaseClass {
    private String name;
    private String region;
    private Double baseRate;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "service_zone_id")
    private List<Address> addresses = new ArrayList<>();
}
