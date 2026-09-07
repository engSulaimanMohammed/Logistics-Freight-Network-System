package com.example.LogisticsNetworkSystem.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

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
