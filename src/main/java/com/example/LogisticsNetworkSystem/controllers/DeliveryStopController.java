package com.example.LogisticsNetworkSystem.controllers;

import com.example.LogisticsNetworkSystem.dtos.DeliveryStopCompleteDTO;
import com.example.LogisticsNetworkSystem.dtos.DeliveryStopDTO;
import com.example.LogisticsNetworkSystem.entities.DeliveryStop;
import com.example.LogisticsNetworkSystem.services.DeliveryStopService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deliveryStop")
public class DeliveryStopController {
    private final DeliveryStopService deliveryStopService;

    public DeliveryStopController(DeliveryStopService deliveryStopService) {
        this.deliveryStopService = deliveryStopService;
    }

    @PostMapping("/add")
    public DeliveryStopDTO add(@Valid @RequestBody DeliveryStopDTO dto) {
        DeliveryStop stop = new DeliveryStop();
        stop.setSequence(dto.getSequence());
        stop.setAddress(dto.getAddress());
        stop.setStatus(dto.getStatus());
        stop.setEta(dto.getEta());
        return DeliveryStopDTO.convertToDTO(deliveryStopService.addDeliveryStop(
                stop, dto.getRouteId(), dto.getShipmentId()));
    }

    @PostMapping("/addToRoute")
    public DeliveryStopDTO addToRoute(@Valid @RequestBody DeliveryStopDTO dto) {
        return add(dto);
    }

    @GetMapping("/getAll")
    public List<DeliveryStopDTO> getAll() {
        return DeliveryStopDTO.convertToDTO(deliveryStopService.getAllDeliveryStops());
    }

    @GetMapping("/getById/{id}")
    public DeliveryStopDTO getById(@PathVariable Long id) {
        return DeliveryStopDTO.convertToDTO(deliveryStopService.getById(id));
    }

    @PutMapping("/update/{id}")
    public DeliveryStopDTO update(@PathVariable Long id, @Valid @RequestBody DeliveryStopDTO dto) {
        return DeliveryStopDTO.convertToDTO(deliveryStopService.updateDeliveryStop(
                id, dto.getSequence(), dto.getAddress(), dto.getStatus(), dto.getEta(),
                dto.getRouteId(), dto.getShipmentId()));
    }

    @PutMapping("/complete")
    public DeliveryStopDTO complete(@Valid @RequestBody DeliveryStopCompleteDTO dto) {
        return DeliveryStopDTO.convertToDTO(deliveryStopService.markComplete(dto.getDeliveryStopId()));
    }

    @DeleteMapping("/delete/{id}")
    public DeliveryStopDTO delete(@PathVariable Long id) {
        DeliveryStopDTO dto = DeliveryStopDTO.convertToDTO(deliveryStopService.getById(id));
        deliveryStopService.deleteById(id);
        return dto;
    }
}
