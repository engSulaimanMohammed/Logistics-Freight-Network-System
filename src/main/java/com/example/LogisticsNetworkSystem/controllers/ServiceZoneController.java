package com.example.LogisticsNetworkSystem.controllers;

import com.example.LogisticsNetworkSystem.dtos.ServiceZoneDTO;
import com.example.LogisticsNetworkSystem.entities.ServiceZone;
import com.example.LogisticsNetworkSystem.services.ServiceZoneService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/serviceZone")
public class ServiceZoneController {

    private final ServiceZoneService serviceZoneService;

    public ServiceZoneController(ServiceZoneService serviceZoneService) {
        this.serviceZoneService = serviceZoneService;
    }

    @PostMapping("/add")
    public ServiceZoneDTO add(@Valid @RequestBody ServiceZoneDTO dto) {
        ServiceZone serviceZone = new ServiceZone();
        serviceZone.setName(dto.getName());
        serviceZone.setRegion(dto.getRegion());
        serviceZone.setBaseRate(dto.getBaseRate());
        return ServiceZoneDTO.convertToDTO(serviceZoneService.addServiceZone(serviceZone));
    }

    @GetMapping("/getAll")
    public List<ServiceZoneDTO> getAll() {
        return ServiceZoneDTO.convertToDTO(serviceZoneService.getAllServiceZones());
    }

    @GetMapping("/getById/{id}")
    public ServiceZoneDTO getById(@PathVariable Long id) {
        return ServiceZoneDTO.convertToDTO(serviceZoneService.getById(id));
    }

    @PutMapping("/update/{id}")
    public ServiceZoneDTO update(@PathVariable Long id, @Valid @RequestBody ServiceZoneDTO dto) {
        return ServiceZoneDTO.convertToDTO(serviceZoneService.updateServiceZone(id, dto.getName(), dto.getRegion(), dto.getBaseRate()));
    }

    @DeleteMapping("/delete/{id}")
    public ServiceZoneDTO delete(@PathVariable Long id) {
        ServiceZoneDTO dto = ServiceZoneDTO.convertToDTO(serviceZoneService.getById(id));
        serviceZoneService.deleteById(id);
        return dto;
    }
}
