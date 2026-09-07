package com.example.LogisticsNetworkSystem.controllers;

import com.example.LogisticsNetworkSystem.dtos.ShipmentCarrierAssignmentDTO;
import com.example.LogisticsNetworkSystem.dtos.ShipmentDTO;
import com.example.LogisticsNetworkSystem.entities.Shipment;
import com.example.LogisticsNetworkSystem.services.ShipmentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Exposes REST operations for shipment management and shipment workflows. */
@RestController
@RequestMapping("/shipment")
public class ShipmentController {
    /** Coordinates shipment persistence and business actions through the service layer. */
    private final ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    /** Creates a shipment from the DTO and associates its warehouse, customer, and carrier. */
    @PostMapping("/add")
    public ShipmentDTO add(@Valid @RequestBody ShipmentDTO dto) {
        Shipment shipment = new Shipment();
        shipment.setShipmentDate(dto.getShipmentDate());
        shipment.setStatus(dto.getStatus());
        shipment.setTotalWeight(dto.getTotalWeight());
        return ShipmentDTO.convertToDTO(shipmentService.addShipment(
                shipment, dto.getWarehouseId(), dto.getCustomerId(), dto.getCarrierId()));
    }

    /** Creates a shipment with its requested shipment item collection. */
    @PostMapping("/create")
    public ShipmentDTO createShipment(@Valid @RequestBody ShipmentDTO dto) {
        Shipment shipment = new Shipment();
        shipment.setShipmentDate(dto.getShipmentDate());
        shipment.setStatus(dto.getStatus());
        return ShipmentDTO.convertToDTO(shipmentService.createShipment(
                shipment, dto.getWarehouseId(), dto.getCustomerId(), dto.getShipmentItems()));
    }

    @GetMapping("/getAll")
    public List<ShipmentDTO> getAll() {
        return ShipmentDTO.convertToDTO(shipmentService.getAllShipments());
    }

    @GetMapping("/getById/{id}")
    public ShipmentDTO getById(@PathVariable Long id) {
        return ShipmentDTO.convertToDTO(shipmentService.getById(id));
    }

    @PutMapping("/update/{id}")
    public ShipmentDTO update(@PathVariable Long id, @Valid @RequestBody ShipmentDTO dto) {
        return ShipmentDTO.convertToDTO(shipmentService.updateShipment(
                id, dto.getShipmentDate(), dto.getStatus(), dto.getTotalWeight(),
                dto.getWarehouseId(), dto.getCustomerId(), dto.getCarrierId()));
    }

    @PutMapping("/assignCarrier")
    public ShipmentDTO assignCarrier(@Valid @RequestBody ShipmentCarrierAssignmentDTO dto) {
        return ShipmentDTO.convertToDTO(shipmentService.assignCarrier(dto.getShipmentId(), dto.getCarrierId()));
    }

    @GetMapping("/byStatus/{status}")
    public List<ShipmentDTO> getByStatus(@PathVariable String status) {
        return ShipmentDTO.convertToDTO(shipmentService.getByStatus(status));
    }

    @GetMapping("/customerHistory/{customerId}")
    public List<ShipmentDTO> getCustomerHistory(@PathVariable Long customerId) {
        return ShipmentDTO.convertToDTO(shipmentService.getCustomerShipmentHistory(customerId));
    }

    @DeleteMapping("/delete/{id}")
    public ShipmentDTO delete(@PathVariable Long id) {
        ShipmentDTO dto = ShipmentDTO.convertToDTO(shipmentService.getById(id));
        shipmentService.deleteById(id);
        return dto;
    }
}
