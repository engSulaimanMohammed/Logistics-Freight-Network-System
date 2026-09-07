package com.example.LogisticsNetworkSystem.services;

import com.example.LogisticsNetworkSystem.dtos.CarrierStatsDTO;
import com.example.LogisticsNetworkSystem.entities.Carrier;
import com.example.LogisticsNetworkSystem.exceptions.ResourceNotFoundException;
import com.example.LogisticsNetworkSystem.repositories.CarrierRepository;
import com.example.LogisticsNetworkSystem.repositories.DriverRepository;
import com.example.LogisticsNetworkSystem.repositories.RouteRepository;
import java.util.Date;
import java.util.List;

import com.example.LogisticsNetworkSystem.repositories.VehicleRepository;
import org.springframework.stereotype.Service;

@Service
public class CarrierService {

    private final CarrierRepository carrierRepository;
    private final VehicleRepository vehicleRepository;
    private final DriverRepository driverRepository;
    private final RouteRepository routeRepository;

    public CarrierService(
            CarrierRepository carrierRepository,
            VehicleRepository vehicleRepository,
            DriverRepository driverRepository,
            RouteRepository routeRepository) {
        this.carrierRepository = carrierRepository;
        this.vehicleRepository = vehicleRepository;
        this.driverRepository = driverRepository;
        this.routeRepository = routeRepository;
    }

    private void validateCarrierData(String name, String contactEmail, String phoneNumber, String country) {
        if (name == null
                || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        if (name.length() > 255) {
            throw new IllegalArgumentException("Name cannot exceed 255 characters");
        }
        if (contactEmail == null || contactEmail.trim().isEmpty()) {
            throw new IllegalArgumentException("ContactEmail cannot be blank");
        }
        if (contactEmail.length() > 255) {
            throw new IllegalArgumentException("ContactEmail cannot exceed 255 characters");
        }
        if (!contactEmail.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Contact email must be valid");
        }
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("PhoneNumber cannot be blank");
        }
        if (phoneNumber.length() > 255) {
            throw new IllegalArgumentException("PhoneNumber cannot exceed 255 characters");
        }
        if (country == null || country.trim().isEmpty()) {
            throw new IllegalArgumentException("Country cannot be blank");
        }
        if (country.length() > 255) {
            throw new IllegalArgumentException("Country cannot exceed 255 characters");
        }
    }

    public Carrier addCarrier(Carrier carrier) {
        if (carrier == null) {
            throw new IllegalArgumentException("Carrier cannot be null");
        }
        validateCarrierData(
                carrier.getName(), carrier.getContactEmail(), carrier.getPhoneNumber(), carrier.getCountry());
        carrier.setActive(true);
        carrier.setCreatedDate(new Date());
        return carrierRepository.save(carrier);
    }

    public List<Carrier> getAllCarriers() {
        return carrierRepository.findAll().stream().filter(Carrier::isActive).toList();
    }

    public Carrier getById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Carrier ID must be greater than zero");
        }
        Carrier carrier = carrierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Carrier not found with id: " + id));
        if (!carrier.isActive()) {
            throw new ResourceNotFoundException("Carrier not found with id: " + id);
        }
        return carrier;
    }

    public Carrier updateCarrier(Long id, String name, String contactEmail, String phoneNumber, String country) {
        validateCarrierData(name, contactEmail, phoneNumber, country);
        Carrier carrier = getById(id);
        carrier.setName(name);
        carrier.setContactEmail(contactEmail);
        carrier.setPhoneNumber(phoneNumber);
        carrier.setCountry(country);
        carrier.setUpdatedDate(new Date());
        return carrierRepository.save(carrier);
    }

public CarrierStatsDTO getStats(Long carrierId) {
    getById(carrierId);
    return CarrierStatsDTO.builder()
            .carrierId(carrierId)
            .vehicles(vehicleRepository.countByCarrier_IdAndIsActiveTrue(carrierId))
            .drivers(driverRepository.countByCarrier_IdAndIsActiveTrue(carrierId))
            .activeRoutes(routeRepository.countActiveRoutesByCarrier(carrierId))
            .build();
}

    public boolean deleteById(Long id) {
        Carrier carrier = getById(id);
        carrier.setActive(false);
        carrier.setUpdatedDate(new Date());
        carrierRepository.save(carrier);
        return true;
    }
}
