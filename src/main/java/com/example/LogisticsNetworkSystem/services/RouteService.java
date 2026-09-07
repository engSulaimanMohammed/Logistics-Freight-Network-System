package com.example.LogisticsNetworkSystem.services;

import com.example.LogisticsNetworkSystem.entities.Driver;
import com.example.LogisticsNetworkSystem.entities.Route;
import com.example.LogisticsNetworkSystem.entities.Shipment;
import com.example.LogisticsNetworkSystem.entities.Vehicle;
import com.example.LogisticsNetworkSystem.exceptions.ResourceNotFoundException;
import java.util.Date;
import java.util.List;

import com.example.LogisticsNetworkSystem.repositories.RouteRepository;
import org.springframework.stereotype.Service;

@Service
public class RouteService {
    private final RouteRepository routeRepository;
    private final VehicleService vehicleService;
    private final DriverService driverService;
    private final ShipmentService shipmentService;

    public RouteService(
            RouteRepository routeRepository,
            VehicleService vehicleService,
            DriverService driverService,
            ShipmentService shipmentService) {
        this.routeRepository = routeRepository;
        this.vehicleService = vehicleService;
        this.driverService = driverService;
        this.shipmentService = shipmentService;
    }

    private void validateRouteData(Date routeDate, String origin, String destination, String status) {
        if (routeDate == null) {
            throw new IllegalArgumentException("Route date cannot be null");
        }
        if (origin == null || origin.trim().isEmpty()) {
            throw new IllegalArgumentException("Origin cannot be blank");
        }
        if (origin.length() > 255) throw new IllegalArgumentException("Origin cannot exceed 255 characters");
        if (destination == null || destination.trim().isEmpty()) throw new IllegalArgumentException("Destination cannot be blank");
        if (destination.length() > 255) throw new IllegalArgumentException("Destination cannot exceed 255 characters");
        if (status == null || status.trim().isEmpty()) throw new IllegalArgumentException("Status cannot be blank");
        if (status.length() > 255) throw new IllegalArgumentException("Status cannot exceed 255 characters");
    }

    public Route addRoute(Route route, Long vehicleId, Long driverId) {
        if (route == null) {
            throw new IllegalArgumentException("Route cannot be null");
        }
        validateRouteData(
                route.getRouteDate(), route.getOrigin(), route.getDestination(), route.getStatus());
        route.setVehicle(vehicleService.getById(vehicleId));
        route.setDriver(driverService.getById(driverId));
        route.setActive(true);
        route.setCreatedDate(new Date());
        return routeRepository.save(route);
    }

    public Route buildRoute(Route route, Long vehicleId, Long driverId, Long shipmentId) {
        if (route == null) throw new IllegalArgumentException("Route cannot be null");
        validateRouteData(route.getRouteDate(), route.getOrigin(), route.getDestination(), route.getStatus());
        Vehicle vehicle = vehicleService.getById(vehicleId);
        Driver driver = driverService.getById(driverId);
        Shipment shipment = shipmentService.getById(shipmentId);

        if (!"Available".equalsIgnoreCase(vehicle.getStatus())) {
            throw new IllegalArgumentException("Vehicle is unavailable");
        }
        if (!"Available".equalsIgnoreCase(driver.getStatus())) {
            throw new IllegalArgumentException("Driver is unavailable");
        }
        if (shipment.getTotalWeight() != null && shipment.getTotalWeight() > vehicle.getCapacityKg()) {
            throw new IllegalArgumentException("Shipment total weight exceeds vehicle capacity");
        }

        route.setVehicle(vehicle);
        route.setDriver(driver);
        route.setActive(true);
        route.setCreatedDate(new Date());
        return routeRepository.save(route);
    }

    public List<Route> getAllRoutes() {
        return routeRepository.findAll().stream().filter(Route::isActive).toList();
    }

    public Route getById(Long id) {
        if (id == null || id <= 0) throw new IllegalArgumentException("Route ID must be greater than zero");
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Route not found with id: " + id));
        if (!route.isActive()) throw new ResourceNotFoundException("Route not found with id: " + id);
        return route;
    }

    public Route updateRoute(Long id, Date routeDate, String origin, String destination, String status,
                             Long vehicleId, Long driverId) {
        validateRouteData(routeDate, origin, destination, status);
        Route route = getById(id);
        route.setRouteDate(routeDate);
        route.setOrigin(origin);
        route.setDestination(destination);
        route.setStatus(status);
        route.setVehicle(vehicleService.getById(vehicleId));
        route.setDriver(driverService.getById(driverId));
        route.setUpdatedDate(new Date());
        return routeRepository.save(route);
    }

    public List<Route> getRoutesForDriverOnDate(Long driverId, Date routeDate) {
        driverService.getById(driverId);
        if (routeDate == null) throw new IllegalArgumentException("Route date cannot be null");
        return routeRepository.findRoutesForDriverOnDate(driverId, routeDate);
    }

    public Route markComplete(Long routeId) {
        Route route = getById(routeId);
        route.setStatus("Complete");
        route.setUpdatedDate(new Date());
        return routeRepository.save(route);
    }

    public boolean deleteById(Long id) {
        Route route = getById(id);
        route.setActive(false);
        route.setUpdatedDate(new Date());
        routeRepository.save(route);
        return true;
    }
}
