package com.example.LogisticsNetworkSystem.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Address extends BaseClass {
    private String street;
    private String city;
    private String postalCode;
    private String country;

    @ManyToOne
    private Customer customer;
}
