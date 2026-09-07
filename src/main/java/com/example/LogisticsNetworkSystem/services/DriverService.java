package com.example.LogisticsNetworkSystem.services;

import com.example.LogisticsNetworkSystem.entities.Carrier;
import com.example.LogisticsNetworkSystem.entities.Driver;
import com.example.LogisticsNetworkSystem.exceptions.ResourceNotFoundException;
import com.example.LogisticsNetworkSystem.repositories.DriverRepository;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DriverService {

    private final DriverRepository driverRepository;
    private final CarrierService carrierService;

    public DriverService(
            DriverRepository driverRepository,
            CarrierService carrierService) {
        this.driverRepository = driverRepository;
        this.carrierService = carrierService;
    }

    private void validateDriverData(String name, String licenseNumber, String phoneNumber, String status) {
        if (name == null
                || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        if (name.length() > 255) {
            throw new IllegalArgumentException("Name cannot exceed 255 characters");
        }
        if (licenseNumber == null
                || licenseNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("LicenseNumber cannot be blank");
        }
        if (licenseNumber.length() > 255) {
            throw new IllegalArgumentException("LicenseNumber cannot exceed 255 characters");
        }
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("PhoneNumber cannot be blank");
        }
        if (phoneNumber.length() > 255) {
            throw new IllegalArgumentException("PhoneNumber cannot exceed 255 characters");
        }
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be blank");
        }
        if (status.length() > 255) {
            throw new IllegalArgumentException("Status cannot exceed 255 characters");
        }
    }

    public Driver addDriver(Driver driver, Long carrierId) {
        if (driver == null) {
            throw new IllegalArgumentException("Driver cannot be null");
        }
        validateDriverData(
                driver.getName(), driver.getLicenseNumber(), driver.getPhoneNumber(), driver.getStatus());
        Carrier carrier = carrierService.getById(carrierId);
        driver.setCarrier(carrier);
        driver.setActive(true);
        driver.setCreatedDate(new Date());
        return driverRepository.save(driver);
    }

    public List<Driver> getAllDrivers() {
        return driverRepository.findAll().stream()
                .filter(Driver::isActive)
                .toList();
    }

    public Driver getById(Long id) {
        if (id == null
                || id <= 0) {
            throw new IllegalArgumentException("Driver ID must be greater than zero");
        }
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found with id: " + id));
        if (!driver.isActive()) {
            throw new ResourceNotFoundException("Driver not found with id: " + id);
        }
        return driver;
    }

    public Driver updateDriver(
            Long id,
            String name,
            String licenseNumber,
            String phoneNumber,
            String status,
            Long carrierId) {
        validateDriverData(name, licenseNumber, phoneNumber, status);
        Driver driver = getById(id);
        Carrier carrier = carrierService.getById(carrierId);
        driver.setName(name);
        driver.setLicenseNumber(licenseNumber);
        driver.setPhoneNumber(phoneNumber);
        driver.setStatus(status);
        driver.setCarrier(carrier);
        driver.setUpdatedDate(new Date());
        return driverRepository.save(driver);
    }

    public boolean deleteById(Long id) {
        Driver driver = getById(id);
        driver.setActive(false);
        driver.setUpdatedDate(new Date());
        driverRepository.save(driver);
        return true;
    }
}
