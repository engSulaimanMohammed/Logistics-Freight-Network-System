package com.example.LogisticsNetworkSystem.services;

import com.example.LogisticsNetworkSystem.dtos.WarehouseStatsDTO;
import com.example.LogisticsNetworkSystem.entities.Warehouse;
import com.example.LogisticsNetworkSystem.exceptions.ResourceNotFoundException;
import com.example.LogisticsNetworkSystem.repositories.InventoryItemRepository;
import com.example.LogisticsNetworkSystem.repositories.ShipmentRepository;
import java.util.Date;
import java.util.List;

import com.example.LogisticsNetworkSystem.repositories.WarehouseRepository;
import org.springframework.stereotype.Service;

@Service
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final ShipmentRepository shipmentRepository;
    private final InventoryItemRepository inventoryItemRepository;

    public WarehouseService(
            WarehouseRepository warehouseRepository,
            ShipmentRepository shipmentRepository,
            InventoryItemRepository inventoryItemRepository) {
        this.warehouseRepository = warehouseRepository;
        this.shipmentRepository = shipmentRepository;
        this.inventoryItemRepository = inventoryItemRepository;
    }

    private void validateWarehouseData(
            String name,
            String location,
            Integer capacity) {
        if (name == null
                || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        if (name.length() > 255) {
            throw new IllegalArgumentException("Name cannot exceed 255 characters");
        }
        if (location == null
                || location.trim().isEmpty()) {
            throw new IllegalArgumentException("Location cannot be blank");
        }
        if (location.length() > 255) {
            throw new IllegalArgumentException("Location cannot exceed 255 characters");
        }
        if (capacity == null || capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero");
        }
    }

    public Warehouse addWarehouse(Warehouse warehouse) {
        if (warehouse == null) {
            throw new IllegalArgumentException("Warehouse cannot be null");
        }
        validateWarehouseData(warehouse.getName(), warehouse.getLocation(), warehouse.getCapacity());
        warehouse.setActive(true);
        warehouse.setCreatedDate(new Date());
        return warehouseRepository.save(warehouse);
    }

    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll().stream()
                .filter(Warehouse::isActive)
                .toList();
    }

    public Warehouse getById(Long id) {
        if (id == null
                || id <= 0) {
            throw new IllegalArgumentException("Warehouse ID must be greater than zero");
        }
        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse not found with id: " + id));
        if (!warehouse.isActive()) {
            throw new ResourceNotFoundException("Warehouse not found with id: " + id);
        }
        return warehouse;
    }

    public Warehouse updateWarehouse(
            Long id,
            String name,
            String location,
            Integer capacity) {
        validateWarehouseData(name, location, capacity);
        Warehouse warehouse = getById(id);
        warehouse.setName(name);
        warehouse.setLocation(location);
        warehouse.setCapacity(capacity);
        warehouse.setUpdatedDate(new Date());
        return warehouseRepository.save(warehouse);
    }

    public WarehouseStatsDTO getStats(Long warehouseId) {
        getById(warehouseId);
    long activeShipments = shipmentRepository.countActiveShipmentsByWarehouse(warehouseId);
    Long totalUnits = inventoryItemRepository.totalInventoryUnitsByWarehouse(warehouseId);
    return WarehouseStatsDTO.builder()
            .warehouseId(warehouseId)
            .activeShipments(activeShipments)
            .totalInventoryUnits(
                    totalUnits == null ? 0 : totalUnits)
            .build();
}

    public boolean deleteById(Long id) {
        Warehouse warehouse = getById(id);
        warehouse.setActive(false);

        warehouse.setUpdatedDate(new Date());
        warehouseRepository.save(warehouse);
        return true;
    }
}
