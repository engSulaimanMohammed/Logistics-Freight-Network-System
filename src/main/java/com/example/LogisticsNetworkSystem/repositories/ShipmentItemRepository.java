package com.example.LogisticsNetworkSystem.repositories;

import com.example.LogisticsNetworkSystem.entities.ShipmentItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentItemRepository extends JpaRepository<ShipmentItem, Long> {
}
