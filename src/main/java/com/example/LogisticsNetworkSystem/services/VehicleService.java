package com.example.LogisticsNetworkSystem.services;

import com.example.LogisticsNetworkSystem.entities.Carrier;
import com.example.LogisticsNetworkSystem.entities.Vehicle;
import com.example.LogisticsNetworkSystem.exceptions.ResourceNotFoundException;
import com.example.LogisticsNetworkSystem.repositories.VehicleRepository;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final CarrierService carrierService;

    public VehicleService(
            VehicleRepository vehicleRepository,
            CarrierService carrierService) {
        this.vehicleRepository = vehicleRepository;
        this.carrierService = carrierService;
    }

    private void validateVehicleData(String plateNumber, String type, Double capacityKg, String status) {
        if (plateNumber == null || plateNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("PlateNumber cannot be blank");
        }
        if (plateNumber.length() > 255) {
            throw new IllegalArgumentException("PlateNumber cannot exceed 255 characters");
        }
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Type cannot be blank");
        }
        if (type.length() > 255) {
            throw new IllegalArgumentException("Type cannot exceed 255 characters");
        }
        if (capacityKg == null || capacityKg <= 0) {
            throw new IllegalArgumentException("CapacityKg must be greater than zero");
        }
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be blank");
        }
        if (status.length() > 255) {
            throw new IllegalArgumentException("Status cannot exceed 255 characters");
        }
    }

    public Vehicle addVehicle(Vehicle vehicle, Long carrierId) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null");
        }
        validateVehicleData(vehicle.getPlateNumber(), vehicle.getType(), vehicle.getCapacityKg(), vehicle.getStatus());
        Carrier carrier = carrierService.getById(carrierId);
        vehicle.setCarrier(carrier);
        vehicle.setActive(true);
        vehicle.setCreatedDate(new Date());
        return vehicleRepository.save(vehicle);
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll().stream().filter(Vehicle::isActive).toList();
    }

    public Vehicle getById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Vehicle ID must be greater than zero");
        }
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));
        if (!vehicle.isActive()) {
            throw new ResourceNotFoundException("Vehicle not found with id: " + id);
        }
        return vehicle;
    }

    public Vehicle updateVehicle(Long id, String plateNumber, String type, Double capacityKg, String status, Long carrierId) {
        validateVehicleData(plateNumber, type, capacityKg, status);
        Vehicle vehicle = getById(id);
        Carrier carrier = carrierService.getById(carrierId);
        vehicle.setPlateNumber(plateNumber);
        vehicle.setType(type);
        vehicle.setCapacityKg(capacityKg);
        vehicle.setStatus(status);
        vehicle.setCarrier(carrier);
        vehicle.setUpdatedDate(new Date());
        return vehicleRepository.save(vehicle);
    }

public List<Vehicle> getAvailableVehicles() {
    return vehicleRepository.findByStatus("Available");
}

    public boolean deleteById(Long id) {
        Vehicle vehicle = getById(id);
        vehicle.setActive(false);
        vehicle.setUpdatedDate(new Date());
        vehicleRepository.save(vehicle);
        return true;
    }
}
