package com.example.LogisticsNetworkSystem.repositories;

import com.example.LogisticsNetworkSystem.entities.Route;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    @Query("SELECT r FROM Route r WHERE r.driver.id = :driverId AND r.routeDate = :routeDate AND r.isActive = true")
    List<Route> findRoutesForDriverOnDate(@Param("driverId") Long driverId, @Param("routeDate") Date routeDate);

    @Query("SELECT COUNT(r) FROM Route r WHERE r.vehicle.carrier.id = :carrierId AND r.isActive = true")
    long countActiveRoutesByCarrier(@Param("carrierId") Long carrierId);
}
