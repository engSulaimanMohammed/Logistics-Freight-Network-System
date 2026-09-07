package com.example.LogisticsNetworkSystem.controllers;

import com.example.LogisticsNetworkSystem.dtos.InventoryItemDTO;
import com.example.LogisticsNetworkSystem.entities.InventoryItem;
import com.example.LogisticsNetworkSystem.services.InventoryItemService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.*;

/** Exposes REST operations for warehouse inventory item management. */
@RestController
@RequestMapping("/inventoryItem")
public class InventoryItemController {

    /** Coordinates inventory persistence and warehouse/product associations. */
    private final InventoryItemService inventoryItemService;

    public InventoryItemController(InventoryItemService inventoryItemService) {
        this.inventoryItemService = inventoryItemService;
    }

    @PostMapping("/add")
    public InventoryItemDTO add(@Valid @RequestBody InventoryItemDTO dto) {
        InventoryItem inventoryItem = new InventoryItem();
        inventoryItem.setQuantity(dto.getQuantity());
        inventoryItem.setShelfLocation(dto.getShelfLocation());
        return InventoryItemDTO.convertToDTO(inventoryItemService.addInventoryItem(inventoryItem, dto.getWarehouseId(), dto.getProductId()));
    }

    @GetMapping("/getAll")
    public List<InventoryItemDTO> getAll() {
        return InventoryItemDTO.convertToDTO(inventoryItemService.getAllInventoryItems());
    }

    @GetMapping("/getById/{id}")
    public InventoryItemDTO getById(@PathVariable Long id) {
        return InventoryItemDTO.convertToDTO(inventoryItemService.getById(id));
    }

    @PutMapping("/update/{id}")
    public InventoryItemDTO update(@PathVariable Long id, @Valid @RequestBody InventoryItemDTO dto) {
        return InventoryItemDTO.convertToDTO(inventoryItemService.updateInventoryItem(id, dto.getQuantity(), dto.getShelfLocation(), dto.getWarehouseId(), dto.getProductId()));
    }

@GetMapping("/belowThreshold/{threshold}")
public List<InventoryItemDTO> getBelowThreshold(@PathVariable Integer threshold) {
    return InventoryItemDTO.convertToDTO(inventoryItemService.getBelowReorderThreshold(threshold));
}

    @DeleteMapping("/delete/{id}")
    public InventoryItemDTO delete(@PathVariable Long id) {
        InventoryItemDTO dto = InventoryItemDTO.convertToDTO(inventoryItemService.getById(id));
        inventoryItemService.deleteById(id);
        return dto;
    }
}
