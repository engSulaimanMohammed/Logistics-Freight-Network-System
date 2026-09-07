package com.example.LogisticsNetworkSystem.controllers;

import com.example.LogisticsNetworkSystem.dtos.ShipmentItemDTO;
import com.example.LogisticsNetworkSystem.entities.ShipmentItem;
import com.example.LogisticsNetworkSystem.services.ShipmentItemService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.*;

/** Exposes REST operations for items associated with shipments. */
@RestController
@RequestMapping("/shipmentItem")
public class ShipmentItemController {

    /** Coordinates shipment item persistence and shipment/product associations. */
    private final ShipmentItemService shipmentItemService;

    public ShipmentItemController(ShipmentItemService shipmentItemService) {
        this.shipmentItemService = shipmentItemService;
    }

    /** Creates an item from the DTO and links it to a shipment and product. */
    @PostMapping("/add")
    public ShipmentItemDTO add(@Valid @RequestBody ShipmentItemDTO dto) {
        ShipmentItem shipmentItem = new ShipmentItem();
        shipmentItem.setQuantity(dto.getQuantity());
        return ShipmentItemDTO.convertToDTO(shipmentItemService.addShipmentItem(shipmentItem, dto.getShipmentId(), dto.getProductId()));
    }

    /** Returns all shipment items as response DTOs. */
    @GetMapping("/getAll")
    public List<ShipmentItemDTO> getAll() {
        return ShipmentItemDTO.convertToDTO(shipmentItemService.getAllShipmentItems());
    }

    /** Retrieves the shipment item identified by the {@code id} path variable. */
    @GetMapping("/getById/{id}")
    public ShipmentItemDTO getById(@PathVariable Long id) {
        return ShipmentItemDTO.convertToDTO(shipmentItemService.getById(id));
    }

    /** Updates the item identified by {@code id} with the validated DTO fields. */
    @PutMapping("/update/{id}")
    public ShipmentItemDTO update(@PathVariable Long id, @Valid @RequestBody ShipmentItemDTO dto) {
        return ShipmentItemDTO.convertToDTO(shipmentItemService.updateShipmentItem(id, dto.getQuantity(), dto.getShipmentId(), dto.getProductId()));
    }

    /** Deletes the selected shipment item and returns its DTO representation. */
    @DeleteMapping("/delete/{id}")
    public ShipmentItemDTO delete(@PathVariable Long id) {
        ShipmentItemDTO dto = ShipmentItemDTO.convertToDTO(shipmentItemService.getById(id));
        shipmentItemService.deleteById(id);
        return dto;
    }
}
