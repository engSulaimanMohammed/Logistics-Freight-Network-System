package com.example.LogisticsNetworkSystem.repositories;

import com.example.LogisticsNetworkSystem.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    @Query("SELECT v FROM Vehicle v WHERE LOWER(v.status) = LOWER(:status) AND v.isActive = true")
    List<Vehicle> findByStatus(@Param("status") String status);

    long countByCarrier_IdAndIsActiveTrue(Long carrierId);
}
