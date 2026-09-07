package com.example.LogisticsNetworkSystem.repositories;

import com.example.LogisticsNetworkSystem.entities.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    @Query("SELECT s FROM Shipment s WHERE LOWER(s.status) = LOWER(:status) AND s.isActive = true")
    List<Shipment> findByStatus(@Param("status") String status);

    @Query("SELECT s FROM Shipment s WHERE s.customer.id = :customerId AND s.isActive = true")
    List<Shipment> findCustomerShipmentHistory(@Param("customerId") Long customerId);

    @Query("SELECT COUNT(s) FROM Shipment s WHERE s.warehouse.id = :warehouseId AND s.isActive = true")
    long countActiveShipmentsByWarehouse(@Param("warehouseId") Long warehouseId);
}
