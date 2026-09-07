package com.example.LogisticsNetworkSystem.services;

import java.util.Date;
import java.util.List;

import com.example.LogisticsNetworkSystem.dtos.ShipmentItemDTO;
import com.example.LogisticsNetworkSystem.entities.Carrier;
import com.example.LogisticsNetworkSystem.entities.Customer;
import com.example.LogisticsNetworkSystem.entities.InventoryItem;
import com.example.LogisticsNetworkSystem.entities.Product;
import com.example.LogisticsNetworkSystem.entities.Shipment;
import com.example.LogisticsNetworkSystem.entities.ShipmentItem;
import com.example.LogisticsNetworkSystem.entities.Warehouse;
import com.example.LogisticsNetworkSystem.exceptions.ResourceNotFoundException;
import com.example.LogisticsNetworkSystem.repositories.InventoryItemRepository;
import com.example.LogisticsNetworkSystem.repositories.ShipmentItemRepository;
import com.example.LogisticsNetworkSystem.repositories.ShipmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ShipmentService {
    private final ShipmentRepository shipmentRepository;
    private final WarehouseService warehouseService;
    private final CustomerService customerService;
    private final CarrierService carrierService;
    private final ProductService productService;
    private final InventoryItemRepository inventoryItemRepository;
    private final ShipmentItemRepository shipmentItemRepository;

    public ShipmentService(
            ShipmentRepository shipmentRepository,
            WarehouseService warehouseService,
            CustomerService customerService,
            CarrierService carrierService,
            ProductService productService,
            InventoryItemRepository inventoryItemRepository,
            ShipmentItemRepository shipmentItemRepository) {
        this.shipmentRepository = shipmentRepository;
        this.warehouseService = warehouseService;
        this.customerService = customerService;
        this.carrierService = carrierService;
        this.productService = productService;
        this.inventoryItemRepository = inventoryItemRepository;
        this.shipmentItemRepository = shipmentItemRepository;
    }

    private void validateShipmentData(Date shipmentDate, String status, Double totalWeight) {
        if (shipmentDate == null) {
            throw new IllegalArgumentException("Shipment date cannot be null");
        }
        if (shipmentDate.after(new Date())) {
            throw new IllegalArgumentException("Shipment date cannot be in the future");
        }
        if (status == null
                || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Shipment status cannot be blank");
        }
        if (status.length() > 255) {
            throw new IllegalArgumentException("Shipment status cannot exceed 255 characters");
        }
        if (totalWeight != null && totalWeight < 0) {
            throw new IllegalArgumentException("Total weight cannot be negative");
        }
    }

    public Shipment addShipment(Shipment shipment, Long warehouseId, Long customerId, Long carrierId) {
        if (shipment == null) {
            throw new IllegalArgumentException("Shipment cannot be null");
        }
        validateShipmentData(shipment.getShipmentDate(), shipment.getStatus(), shipment.getTotalWeight());
        Warehouse warehouse = warehouseService.getById(warehouseId);
        Customer customer = customerService.getById(customerId);
        Carrier carrier = carrierId == null
                ? null
                : carrierService.getById(carrierId);
        shipment.setWarehouse(warehouse);
        shipment.setCustomer(customer);
        shipment.setCarrier(carrier);
        shipment.setActive(true);
        shipment.setCreatedDate(new Date());
        return shipmentRepository.save(shipment);
    }

    @Transactional
    public Shipment createShipment(
            Shipment shipment,
            Long warehouseId,
            Long customerId,
            List<ShipmentItemDTO> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Shipment must contain ShipmentItems");
        }
        Warehouse warehouse = warehouseService.getById(warehouseId);
        Customer customer = customerService.getById(customerId);
        validateShipmentData(shipment.getShipmentDate(), shipment.getStatus(), 0.0);

        double totalWeight = 0.0;
        for (ShipmentItemDTO itemDTO : items) {
            Product product = productService.getById(itemDTO.getProductId());
            InventoryItem inventoryItem = inventoryItemRepository
                    .findByWarehouse_IdAndProduct_IdAndIsActiveTrue(warehouseId, product.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Product is out of stock in this warehouse"));
            if (inventoryItem.getQuantity()
                    < itemDTO.getQuantity()) {
                throw new IllegalArgumentException("Product is out of stock in the requested quantity");
            }
            inventoryItem.setQuantity(inventoryItem.getQuantity() - itemDTO.getQuantity());
            inventoryItem.setUpdatedDate(new Date());
            inventoryItemRepository.save(inventoryItem);
            totalWeight += product.getWeightKg() * itemDTO.getQuantity();
        }

        shipment.setWarehouse(warehouse);
        shipment.setCustomer(customer);
        shipment.setCarrier(null);
        shipment.setTotalWeight(totalWeight);
        shipment.setActive(true);
        shipment.setCreatedDate(new Date());
        Shipment savedShipment = shipmentRepository.save(shipment);

        for (ShipmentItemDTO itemDTO : items) {
            ShipmentItem shipmentItem = new ShipmentItem();
            shipmentItem.setQuantity(itemDTO.getQuantity());
            shipmentItem.setShipment(savedShipment);
            shipmentItem.setProduct(productService.getById(itemDTO.getProductId()));
            shipmentItem.setActive(true);
            shipmentItem.setCreatedDate(new Date());
            shipmentItemRepository.save(shipmentItem);
        }
        return getById(savedShipment.getId());
    }

    public List<Shipment> getAllShipments() {
        return shipmentRepository.findAll().stream().filter(Shipment::isActive).toList();
    }

    public Shipment getById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Shipment ID must be greater than zero");
        }
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shipment not found with id: " + id));
        if (!shipment.isActive()) {
            throw new ResourceNotFoundException("Shipment not found with id: " + id);
        }
        return shipment;
    }

    public Shipment updateShipment(Long id, Date shipmentDate, String status, Double totalWeight,
                                   Long warehouseId, Long customerId, Long carrierId) {
        validateShipmentData(shipmentDate, status, totalWeight);
        Shipment shipment = getById(id);
        shipment.setShipmentDate(shipmentDate);
        shipment.setStatus(status);
        shipment.setTotalWeight(totalWeight);
        shipment.setWarehouse(warehouseService.getById(warehouseId));
        shipment.setCustomer(customerService.getById(customerId));
        shipment.setCarrier(carrierId == null ? null : carrierService.getById(carrierId));
        shipment.setUpdatedDate(new Date());
        return shipmentRepository.save(shipment);
    }

    public Shipment assignCarrier(Long shipmentId, Long carrierId) {
        Shipment shipment = getById(shipmentId);
        shipment.setCarrier(carrierService.getById(carrierId));
        shipment.setUpdatedDate(new Date());
        return shipmentRepository.save(shipment);
    }

    public Shipment updateStatus(Long shipmentId, String status) {
        Shipment shipment = getById(shipmentId);
        shipment.setStatus(status);
        shipment.setUpdatedDate(new Date());
        return shipmentRepository.save(shipment);
    }

    public List<Shipment> getByStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be blank");
        }
        return shipmentRepository.findByStatus(status);
    }

    public List<Shipment> getCustomerShipmentHistory(Long customerId) {
        customerService.getById(customerId);
        return shipmentRepository.findCustomerShipmentHistory(customerId);
    }

    public boolean deleteById(Long id) {
        Shipment shipment = getById(id);
        shipment.setActive(false);
        shipment.setUpdatedDate(new Date());
        shipmentRepository.save(shipment);
        return true;
    }
}
