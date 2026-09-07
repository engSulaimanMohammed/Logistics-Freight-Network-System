package com.example.LogisticsNetworkSystem.controllers;

import com.example.LogisticsNetworkSystem.dtos.RouteBuildDTO;
import com.example.LogisticsNetworkSystem.dtos.RouteDTO;
import com.example.LogisticsNetworkSystem.entities.Route;
import com.example.LogisticsNetworkSystem.services.RouteService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/** Exposes REST operations for route planning and route management. */
@RestController
@RequestMapping("/route")
public class RouteController {
    /** Coordinates route persistence and planning workflows through the service layer. */
    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    /** Creates a route from the DTO and associates it with a vehicle and driver. */
    @PostMapping("/add")
    public RouteDTO add(@Valid @RequestBody RouteDTO dto) {
        Route route = new Route();
        route.setRouteDate(dto.getRouteDate());
        route.setOrigin(dto.getOrigin());
        route.setDestination(dto.getDestination());
        route.setStatus(dto.getStatus());
        return RouteDTO.convertToDTO(routeService.addRoute(route, dto.getVehicleId(), dto.getDriverId()));
    }

    /** Builds a route using the supplied vehicle, driver, and shipment relationships. */
    @PostMapping("/build")
    public RouteDTO build(@Valid @RequestBody RouteBuildDTO dto) {
        Route route = new Route();
        route.setRouteDate(dto.getRouteDate());
        route.setOrigin(dto.getOrigin());
        route.setDestination(dto.getDestination());
        route.setStatus(dto.getStatus());
        return RouteDTO.convertToDTO(routeService.buildRoute(
                route, dto.getVehicleId(), dto.getDriverId(), dto.getShipmentId()));
    }

    /** Returns all routes as response DTOs. */
    @GetMapping("/getAll")
    public List<RouteDTO> getAll() {
        return RouteDTO.convertToDTO(routeService.getAllRoutes());
    }

    /** Retrieves the route identified by the {@code id} path variable. */
    @GetMapping("/getById/{id}")
    public RouteDTO getById(@PathVariable Long id) {
        return RouteDTO.convertToDTO(routeService.getById(id));
    }

    /** Updates the route identified by {@code id} with the validated request DTO. */
    @PutMapping("/update/{id}")
    public RouteDTO update(@PathVariable Long id, @Valid @RequestBody RouteDTO dto) {
        return RouteDTO.convertToDTO(routeService.updateRoute(
                id, dto.getRouteDate(), dto.getOrigin(), dto.getDestination(), dto.getStatus(),
                dto.getVehicleId(), dto.getDriverId()));
    }

    @GetMapping("/byDriverAndDate/{driverId}")
    public List<RouteDTO> byDriverAndDate(@PathVariable Long driverId,
                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date routeDate) {
        return RouteDTO.convertToDTO(routeService.getRoutesForDriverOnDate(driverId, routeDate));
    }

    @DeleteMapping("/delete/{id}")
    public RouteDTO delete(@PathVariable Long id) {
        RouteDTO dto = RouteDTO.convertToDTO(routeService.getById(id));
        routeService.deleteById(id);
        return dto;
    }
}
