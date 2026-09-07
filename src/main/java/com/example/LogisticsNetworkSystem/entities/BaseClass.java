package com.example.LogisticsNetworkSystem.entities;

import java.util.Date;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class BaseClass {
    @Id
    @GeneratedValue
    private Long id;
    private boolean isActive;

    private Date createdDate;
    private Date updatedDate;
}
