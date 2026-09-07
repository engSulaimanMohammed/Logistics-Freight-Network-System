package com.example.LogisticsNetworkSystem.repositories;

import com.example.LogisticsNetworkSystem.entities.TrackingEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackingEventRepository extends JpaRepository<TrackingEvent, Long> {
}
