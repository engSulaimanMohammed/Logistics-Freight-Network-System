package com.example.LogisticsNetworkSystem.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class DeliveryStop extends BaseClass {

    private Integer sequence;
    private String address;
    private String status;
    private Date eta;

    @ManyToOne
    private Route route;

    @ManyToOne
    private Shipment shipment;
}
