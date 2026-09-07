package com.example.LogisticsNetworkSystem.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Staff extends BaseClass {
    private String name;
    private String role;
    private String phoneNumber;

    @ManyToOne
    private Warehouse warehouse;
}
