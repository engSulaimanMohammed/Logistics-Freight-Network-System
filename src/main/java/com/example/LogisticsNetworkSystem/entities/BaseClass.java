package com.example.LogisticsNetworkSystem.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public class BaseClass {
    @Id
    @GeneratedValue
    private Long id;
    private boolean isActive;
    private Date createdDate;
    private Date updatedDate;
}
