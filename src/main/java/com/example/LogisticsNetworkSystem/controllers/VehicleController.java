package com.example.LogisticsNetworkSystem.controllers;

import com.example.LogisticsNetworkSystem.dtos.VehicleDTO;
import com.example.LogisticsNetworkSystem.entities.Vehicle;
import com.example.LogisticsNetworkSystem.services.VehicleService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.*;

/** Exposes REST operations for vehicle management and availability queries. */
@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    /** Coordinates vehicle persistence and carrier association through the service layer. */
    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    /** Creates a vehicle from the validated DTO and associates it with a carrier. */
    @PostMapping("/add")
    public VehicleDTO add(@Valid @RequestBody VehicleDTO dto) {
        Vehicle vehicle = new Vehicle();
        vehicle.setPlateNumber(dto.getPlateNumber());
        vehicle.setType(dto.getType());
        vehicle.setCapacityKg(dto.getCapacityKg());
        vehicle.setStatus(dto.getStatus());
        return VehicleDTO.convertToDTO(vehicleService.addVehicle(vehicle, dto.getCarrierId()));
    }

    /** Returns all vehicles as response DTOs. */
    @GetMapping("/getAll")
    public List<VehicleDTO> getAll() {
        return VehicleDTO.convertToDTO(vehicleService.getAllVehicles());
    }

    /** Retrieves the vehicle identified by the {@code id} path variable. */
    @GetMapping("/getById/{id}")
    public VehicleDTO getById(@PathVariable Long id) {
        return VehicleDTO.convertToDTO(vehicleService.getById(id));
    }

    @PutMapping("/update/{id}")
    public VehicleDTO update(@PathVariable Long id, @Valid @RequestBody VehicleDTO dto) {
        return VehicleDTO.convertToDTO(vehicleService.updateVehicle(id, dto.getPlateNumber(), dto.getType(), dto.getCapacityKg(), dto.getStatus(), dto.getCarrierId()));
    }

@GetMapping("/available")
public List<VehicleDTO> getAvailable() {
    return VehicleDTO.convertToDTO(vehicleService.getAvailableVehicles());
}

    @DeleteMapping("/delete/{id}")
    public VehicleDTO delete(@PathVariable Long id) {
        VehicleDTO dto = VehicleDTO.convertToDTO(vehicleService.getById(id));
        vehicleService.deleteById(id);
        return dto;
    }
}
