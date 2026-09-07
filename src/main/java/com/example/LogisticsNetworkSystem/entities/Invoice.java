package com.example.LogisticsNetworkSystem.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@Entity
public class Invoice extends BaseClass {
    private Double amount;
    private String status;
    private Date issuedDate;

    @ManyToOne
    private Shipment shipment;

    @ManyToOne
    private Customer customer;
}
