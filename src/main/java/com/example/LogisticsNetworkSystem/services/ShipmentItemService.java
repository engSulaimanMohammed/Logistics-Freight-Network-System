package com.example.LogisticsNetworkSystem.services;

import com.example.LogisticsNetworkSystem.entities.Product;
import com.example.LogisticsNetworkSystem.entities.Shipment;
import com.example.LogisticsNetworkSystem.entities.ShipmentItem;
import com.example.LogisticsNetworkSystem.exceptions.ResourceNotFoundException;
import com.example.LogisticsNetworkSystem.repositories.ShipmentItemRepository;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ShipmentItemService {

    private final ShipmentItemRepository shipmentItemRepository;
    private final ShipmentService shipmentService;
    private final ProductService productService;

    public ShipmentItemService(
            ShipmentItemRepository shipmentItemRepository,
            ShipmentService shipmentService,
            ProductService productService) {
        this.shipmentItemRepository = shipmentItemRepository;
        this.shipmentService = shipmentService;
        this.productService = productService;
    }

    private void validateShipmentItemData(Integer quantity) {
        if (quantity == null
                || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
    }

    public ShipmentItem addShipmentItem(ShipmentItem shipmentItem, Long shipmentId, Long productId) {
        if (shipmentItem == null) {
            throw new IllegalArgumentException("ShipmentItem cannot be null");
        }
        validateShipmentItemData(shipmentItem.getQuantity());
        Shipment shipment = shipmentService.getById(shipmentId);
        shipmentItem.setShipment(shipment);

        Product product = productService.getById(productId);
        shipmentItem.setProduct(product);
        shipmentItem.setActive(true);
        shipmentItem.setCreatedDate(new Date());
        return shipmentItemRepository.save(shipmentItem);
    }

    public List<ShipmentItem> getAllShipmentItems() {
        return shipmentItemRepository.findAll().stream()
                .filter(ShipmentItem::isActive)
                .toList();
    }

    public ShipmentItem getById(Long id) {
        if (id == null
                || id <= 0) {
            throw new IllegalArgumentException("ShipmentItem ID must be greater than zero");
        }
        ShipmentItem shipmentItem = shipmentItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ShipmentItem not found with id: " + id));
        if (!shipmentItem.isActive()) {
            throw new ResourceNotFoundException("ShipmentItem not found with id: " + id);
        }
        return shipmentItem;
    }

    public ShipmentItem updateShipmentItem(
            Long id,
            Integer quantity,
            Long shipmentId,
            Long productId) {
        validateShipmentItemData(quantity);
        ShipmentItem shipmentItem = getById(id);
        Shipment shipment = shipmentService.getById(shipmentId);
        Product product = productService.getById(productId);
        shipmentItem.setQuantity(quantity);
        shipmentItem.setShipment(shipment);
        shipmentItem.setProduct(product);

        shipmentItem.setUpdatedDate(new Date());
        return shipmentItemRepository.save(shipmentItem);
    }

    public boolean deleteById(Long id) {
        ShipmentItem shipmentItem = getById(id);
        shipmentItem.setActive(false);

        shipmentItem.setUpdatedDate(new Date());
        shipmentItemRepository.save(shipmentItem);

        return true;
    }
}
