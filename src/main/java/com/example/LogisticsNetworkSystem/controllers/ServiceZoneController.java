package com.example.LogisticsNetworkSystem.controllers;

import com.example.LogisticsNetworkSystem.dtos.ServiceZoneDTO;
import com.example.LogisticsNetworkSystem.entities.ServiceZone;
import com.example.LogisticsNetworkSystem.services.ServiceZoneService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.*;

/** Exposes REST operations for service zone management. */
@RestController
@RequestMapping("/serviceZone")
public class ServiceZoneController {

    /** Coordinates service zone persistence and lookups through the service layer. */
    private final ServiceZoneService serviceZoneService;

    public ServiceZoneController(ServiceZoneService serviceZoneService) {
        this.serviceZoneService = serviceZoneService;
    }

    @PostMapping("/add")
    // Creates a service zone from the validated request DTO.
    public ServiceZoneDTO add(@Valid @RequestBody ServiceZoneDTO dto) {
        ServiceZone serviceZone = new ServiceZone();
        serviceZone.setName(dto.getName());
        serviceZone.setRegion(dto.getRegion());
        serviceZone.setBaseRate(dto.getBaseRate());
        return ServiceZoneDTO.convertToDTO(serviceZoneService.addServiceZone(serviceZone));
    }

    @GetMapping("/getAll")
    // Retrieves all service zones as response DTOs.
    public List<ServiceZoneDTO> getAll() {
        return ServiceZoneDTO.convertToDTO(serviceZoneService.getAllServiceZones());
    }

    @GetMapping("/getById/{id}")
    // Retrieves a service zone by its unique identifier.
    public ServiceZoneDTO getById(@PathVariable Long id) {
        return ServiceZoneDTO.convertToDTO(serviceZoneService.getById(id));
    }

    @PutMapping("/update/{id}")
    // Updates the service zone identified by the path variable.
    public ServiceZoneDTO update(@PathVariable Long id, @Valid @RequestBody ServiceZoneDTO dto) {
        return ServiceZoneDTO.convertToDTO(serviceZoneService.updateServiceZone(id, dto.getName(), dto.getRegion(), dto.getBaseRate()));
    }

    @DeleteMapping("/delete/{id}")
    // Removes the selected service zone and returns its DTO representation.
    public ServiceZoneDTO delete(@PathVariable Long id) {
        ServiceZoneDTO dto = ServiceZoneDTO.convertToDTO(serviceZoneService.getById(id));
        serviceZoneService.deleteById(id);
        return dto;
    }
}
