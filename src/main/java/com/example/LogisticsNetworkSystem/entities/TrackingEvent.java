package com.example.LogisticsNetworkSystem.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@Entity
public class TrackingEvent extends BaseClass {
    private Date eventTime;
    private String location;
    private String status;
    private String note;

    @ManyToOne
    private Shipment shipment;
}
