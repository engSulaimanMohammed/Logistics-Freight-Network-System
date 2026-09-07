package com.example.LogisticsNetworkSystem.repositories;

import com.example.LogisticsNetworkSystem.entities.InventoryItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {
    Optional<InventoryItem> findByWarehouse_IdAndProduct_IdAndIsActiveTrue(
            Long warehouseId,
            Long productId
    );

    @Query(
            value = "SELECT i FROM InventoryItem i "
                    + "WHERE i.quantity < :threshold "
                    + "AND i.isActive = true"
    )
    List<InventoryItem> findBelowReorderThreshold(@Param("threshold") Integer threshold);

    @Query(
            value = "SELECT COALESCE(SUM(i.quantity), 0) FROM InventoryItem i "
                    + "WHERE i.warehouse.id = :warehouseId "
                    + "AND i.isActive = true"
    )
    Long totalInventoryUnitsByWarehouse(@Param("warehouseId") Long warehouseId);
}
