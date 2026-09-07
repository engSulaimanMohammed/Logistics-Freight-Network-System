package com.example.LogisticsNetworkSystem.repositories;

import com.example.LogisticsNetworkSystem.entities.ShipmentItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentItemRepository extends JpaRepository<ShipmentItem, Long> {
}
