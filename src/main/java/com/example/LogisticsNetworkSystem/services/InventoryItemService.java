package com.example.LogisticsNetworkSystem.services;

import com.example.LogisticsNetworkSystem.entities.InventoryItem;
import com.example.LogisticsNetworkSystem.entities.Product;
import com.example.LogisticsNetworkSystem.entities.Warehouse;
import com.example.LogisticsNetworkSystem.exceptions.ResourceNotFoundException;
import com.example.LogisticsNetworkSystem.repositories.InventoryItemRepository;
import com.example.LogisticsNetworkSystem.services.ProductService;
import com.example.LogisticsNetworkSystem.services.WarehouseService;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class InventoryItemService {

    private final InventoryItemRepository inventoryItemRepository;
    private final WarehouseService warehouseService;
    private final ProductService productService;

    public InventoryItemService(InventoryItemRepository inventoryItemRepository, WarehouseService warehouseService, ProductService productService) {
        this.inventoryItemRepository = inventoryItemRepository;
        this.warehouseService = warehouseService;
        this.productService = productService;
    }

    private void validateInventoryItemData(Integer quantity, String shelfLocation) {
        if (quantity == null || quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        if (shelfLocation == null || shelfLocation.trim().isEmpty()) {
            throw new IllegalArgumentException("ShelfLocation cannot be blank");
        }
        if (shelfLocation.length() > 255) {
            throw new IllegalArgumentException("ShelfLocation cannot exceed 255 characters");
        }
    }

    public InventoryItem addInventoryItem(InventoryItem inventoryItem, Long warehouseId, Long productId) {
        if (inventoryItem == null) {
            throw new IllegalArgumentException("InventoryItem cannot be null");
        }
        validateInventoryItemData(inventoryItem.getQuantity(), inventoryItem.getShelfLocation());
        Warehouse warehouse = warehouseService.getById(warehouseId);
        inventoryItem.setWarehouse(warehouse);
        Product product = productService.getById(productId);
        inventoryItem.setProduct(product);
        inventoryItem.setActive(true);
        inventoryItem.setCreatedDate(new Date());
        return inventoryItemRepository.save(inventoryItem);
    }

    public List<InventoryItem> getAllInventoryItems() {
        return inventoryItemRepository.findAll().stream().filter(InventoryItem::isActive).toList();
    }

    public InventoryItem getById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("InventoryItem ID must be greater than zero");
        }
        InventoryItem inventoryItem = inventoryItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("InventoryItem not found with id: " + id));
        if (!inventoryItem.isActive()) {
            throw new ResourceNotFoundException("InventoryItem not found with id: " + id);
        }
        return inventoryItem;
    }

    public InventoryItem updateInventoryItem(Long id, Integer quantity, String shelfLocation, Long warehouseId, Long productId) {
        validateInventoryItemData(quantity, shelfLocation);
        InventoryItem inventoryItem = getById(id);
        Warehouse warehouse = warehouseService.getById(warehouseId);
        Product product = productService.getById(productId);
        inventoryItem.setQuantity(quantity);
        inventoryItem.setShelfLocation(shelfLocation);
        inventoryItem.setWarehouse(warehouse);
        inventoryItem.setProduct(product);
        inventoryItem.setUpdatedDate(new Date());
        return inventoryItemRepository.save(inventoryItem);
    }

public List<InventoryItem> getBelowReorderThreshold(Integer threshold) {
    if (threshold == null || threshold < 0) {
        throw new IllegalArgumentException("Threshold cannot be negative");
    }
    return inventoryItemRepository.findBelowReorderThreshold(threshold);
}

    public boolean deleteById(Long id) {
        InventoryItem inventoryItem = getById(id);
        inventoryItem.setActive(false);
        inventoryItem.setUpdatedDate(new Date());
        inventoryItemRepository.save(inventoryItem);
        return true;
    }
}
