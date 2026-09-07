package com.example.LogisticsNetworkSystem.repositories;

import com.example.LogisticsNetworkSystem.entities.DeliveryStop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryStopRepository extends JpaRepository<DeliveryStop, Long> {
    boolean existsByRoute_IdAndSequenceAndIsActiveTrue(Long routeId, Integer sequence);
    long countByRoute_IdAndIsActiveTrue(Long routeId);
    long countByRoute_IdAndStatusIgnoreCaseAndIsActiveTrue(Long routeId, String status);
}
