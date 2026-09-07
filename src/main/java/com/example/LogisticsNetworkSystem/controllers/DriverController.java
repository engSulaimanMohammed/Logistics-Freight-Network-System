package com.example.LogisticsNetworkSystem.controllers;

import com.example.LogisticsNetworkSystem.dtos.DriverDTO;
import com.example.LogisticsNetworkSystem.entities.Driver;
import com.example.LogisticsNetworkSystem.services.DriverService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.*;

/** Exposes REST operations for driver management. */
@RestController
@RequestMapping("/driver")
public class DriverController {

    /** Coordinates driver persistence and carrier association through the service layer. */
    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    /** Creates a driver from the validated DTO and associates it with a carrier. */
    @PostMapping("/add")
    public DriverDTO add(@Valid @RequestBody DriverDTO dto) {
        Driver driver = new Driver();
        driver.setName(dto.getName());
        driver.setLicenseNumber(dto.getLicenseNumber());
        driver.setPhoneNumber(dto.getPhoneNumber());
        driver.setStatus(dto.getStatus());
        return DriverDTO.convertToDTO(driverService.addDriver(driver, dto.getCarrierId()));
    }

    /** Returns all drivers as response DTOs. */
    @GetMapping("/getAll")
    public List<DriverDTO> getAll() {
        return DriverDTO.convertToDTO(driverService.getAllDrivers());
    }

    /** Retrieves the driver identified by the {@code id} path variable. */
    @GetMapping("/getById/{id}")
    public DriverDTO getById(@PathVariable Long id) {
        return DriverDTO.convertToDTO(driverService.getById(id));
    }

    /** Updates the driver identified by {@code id} with the validated DTO fields. */
    @PutMapping("/update/{id}")
    public DriverDTO update(@PathVariable Long id, @Valid @RequestBody DriverDTO dto) {
        return DriverDTO.convertToDTO(driverService.updateDriver(id, dto.getName(), dto.getLicenseNumber(), dto.getPhoneNumber(), dto.getStatus(), dto.getCarrierId()));
    }

    /** Deletes the selected driver and returns its DTO representation. */
    @DeleteMapping("/delete/{id}")
    public DriverDTO delete(@PathVariable Long id) {
        DriverDTO dto = DriverDTO.convertToDTO(driverService.getById(id));
        driverService.deleteById(id);
        return dto;
    }
}
