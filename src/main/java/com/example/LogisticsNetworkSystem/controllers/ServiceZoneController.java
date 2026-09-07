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

    /** Creates a service zone from the validated request DTO. */
    @PostMapping("/add")
    public ServiceZoneDTO add(@Valid @RequestBody ServiceZoneDTO dto) {
        ServiceZone serviceZone = new ServiceZone();
        serviceZone.setName(dto.getName());
        serviceZone.setRegion(dto.getRegion());
        serviceZone.setBaseRate(dto.getBaseRate());
        return ServiceZoneDTO.convertToDTO(serviceZoneService.addServiceZone(serviceZone));
    }

    /** Returns all service zones as response DTOs. */
    @GetMapping("/getAll")
    public List<ServiceZoneDTO> getAll() {
        return ServiceZoneDTO.convertToDTO(serviceZoneService.getAllServiceZones());
    }

    /** Retrieves the service zone identified by the {@code id} path variable. */
    @GetMapping("/getById/{id}")
    public ServiceZoneDTO getById(@PathVariable Long id) {
        return ServiceZoneDTO.convertToDTO(serviceZoneService.getById(id));
    }

    /** Updates the service zone identified by {@code id} using the validated DTO. */
    @PutMapping("/update/{id}")
    public ServiceZoneDTO update(@PathVariable Long id, @Valid @RequestBody ServiceZoneDTO dto) {
        return ServiceZoneDTO.convertToDTO(serviceZoneService.updateServiceZone(id, dto.getName(), dto.getRegion(), dto.getBaseRate()));
    }

    /** Deletes the selected service zone and returns its DTO representation. */
    @DeleteMapping("/delete/{id}")
    public ServiceZoneDTO delete(@PathVariable Long id) {
        ServiceZoneDTO dto = ServiceZoneDTO.convertToDTO(serviceZoneService.getById(id));
        serviceZoneService.deleteById(id);
        return dto;
    }
}
