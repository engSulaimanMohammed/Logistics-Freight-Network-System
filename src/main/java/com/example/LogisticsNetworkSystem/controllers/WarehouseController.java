package com.example.LogisticsNetworkSystem.controllers;

import com.example.LogisticsNetworkSystem.dtos.WarehouseDTO;
import com.example.LogisticsNetworkSystem.dtos.WarehouseStatsDTO;
import com.example.LogisticsNetworkSystem.entities.Warehouse;
import com.example.LogisticsNetworkSystem.services.WarehouseService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.*;

/** Exposes REST operations for warehouse management and warehouse statistics. */
@RestController
@RequestMapping("/warehouse")
public class WarehouseController {

    /** Coordinates warehouse persistence and business queries through the service layer. */
    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    /** Creates a warehouse from the validated request DTO. */
    @PostMapping("/add")
    public WarehouseDTO add(@Valid @RequestBody WarehouseDTO dto) {
        Warehouse warehouse = new Warehouse();
        warehouse.setName(dto.getName());
        warehouse.setLocation(dto.getLocation());
        warehouse.setCapacity(dto.getCapacity());
        return WarehouseDTO.convertToDTO(warehouseService.addWarehouse(warehouse));
    }

    /** Returns all warehouses as response DTOs. */
    @GetMapping("/getAll")
    public List<WarehouseDTO> getAll() {
        return WarehouseDTO.convertToDTO(warehouseService.getAllWarehouses());
    }

    @GetMapping("/getById/{id}")
    public WarehouseDTO getById(@PathVariable Long id) {
        return WarehouseDTO.convertToDTO(warehouseService.getById(id));
    }

    @PutMapping("/update/{id}")
    public WarehouseDTO update(@PathVariable Long id, @Valid @RequestBody WarehouseDTO dto) {
        return WarehouseDTO.convertToDTO(warehouseService.updateWarehouse(id, dto.getName(), dto.getLocation(), dto.getCapacity()));
    }

@GetMapping("/stats/{id}")
public WarehouseStatsDTO getStats(@PathVariable Long id) {
    return warehouseService.getStats(id);
}

    @DeleteMapping("/delete/{id}")
    public WarehouseDTO delete(@PathVariable Long id) {
        WarehouseDTO dto = WarehouseDTO.convertToDTO(warehouseService.getById(id));
        warehouseService.deleteById(id);
        return dto;
    }
}
