package com.example.LogisticsNetworkSystem.controllers;

import com.example.LogisticsNetworkSystem.dtos.CarrierDTO;
import com.example.LogisticsNetworkSystem.dtos.CarrierStatsDTO;
import com.example.LogisticsNetworkSystem.entities.Carrier;
import com.example.LogisticsNetworkSystem.services.CarrierService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.*;

/** Exposes REST operations for carrier management and carrier statistics. */
@RestController
@RequestMapping("/carrier")
public class CarrierController {

    /** Coordinates carrier persistence and business queries through the service layer. */
    private final CarrierService carrierService;

    public CarrierController(CarrierService carrierService) {
        this.carrierService = carrierService;
    }

    /** Creates a carrier from the validated request DTO. */
    @PostMapping("/add")
    public CarrierDTO add(@Valid @RequestBody CarrierDTO dto) {
        Carrier carrier = new Carrier();
        carrier.setName(dto.getName());
        carrier.setContactEmail(dto.getContactEmail());
        carrier.setPhoneNumber(dto.getPhoneNumber());
        carrier.setCountry(dto.getCountry());
        return CarrierDTO.convertToDTO(carrierService.addCarrier(carrier));
    }

    /** Returns all carriers as response DTOs. */
    @GetMapping("/getAll")
    public List<CarrierDTO> getAll() {
        return CarrierDTO.convertToDTO(carrierService.getAllCarriers());
    }

    @GetMapping("/getById/{id}")
    public CarrierDTO getById(@PathVariable Long id) {
        return CarrierDTO.convertToDTO(carrierService.getById(id));
    }

    @PutMapping("/update/{id}")
    public CarrierDTO update(@PathVariable Long id, @Valid @RequestBody CarrierDTO dto) {
        return CarrierDTO.convertToDTO(carrierService.updateCarrier(id, dto.getName(), dto.getContactEmail(), dto.getPhoneNumber(), dto.getCountry()));
    }

@GetMapping("/stats/{id}")
public CarrierStatsDTO getStats(@PathVariable Long id) {
    return carrierService.getStats(id);
}

    @DeleteMapping("/delete/{id}")
    public CarrierDTO delete(@PathVariable Long id) {
        CarrierDTO dto = CarrierDTO.convertToDTO(carrierService.getById(id));
        carrierService.deleteById(id);
        return dto;
    }
}
