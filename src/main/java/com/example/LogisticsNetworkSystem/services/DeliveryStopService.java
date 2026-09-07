package com.example.LogisticsNetworkSystem.services;

import com.example.LogisticsNetworkSystem.entities.DeliveryStop;
import com.example.LogisticsNetworkSystem.entities.Route;
import com.example.LogisticsNetworkSystem.entities.Shipment;
import com.example.LogisticsNetworkSystem.exceptions.ResourceNotFoundException;
import java.util.Date;
import java.util.List;

import com.example.LogisticsNetworkSystem.repositories.DeliveryStopRepository;
import org.springframework.stereotype.Service;

@Service
public class DeliveryStopService {
    private final DeliveryStopRepository deliveryStopRepository;
    private final RouteService routeService;
    private final ShipmentService shipmentService;

    public DeliveryStopService(
            DeliveryStopRepository deliveryStopRepository,
            RouteService routeService,
            ShipmentService shipmentService) {
        this.deliveryStopRepository = deliveryStopRepository;
        this.routeService = routeService;
        this.shipmentService = shipmentService;
    }

    private void validateData(Integer sequence, String address, String status, Date eta) {
        if (sequence == null || sequence <= 0) {
            throw new IllegalArgumentException("Sequence must be greater than zero");
        }
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be blank");
        }
        if (address.length() > 255) throw new IllegalArgumentException("Address cannot exceed 255 characters");
        if (status == null || status.trim().isEmpty()) throw new IllegalArgumentException("Status cannot be blank");
        if (status.length() > 255) throw new IllegalArgumentException("Status cannot exceed 255 characters");
        if (eta == null) throw new IllegalArgumentException("ETA cannot be null");
        if (!eta.after(new Date())) throw new IllegalArgumentException("ETA must be in the future");
    }

    public DeliveryStop addDeliveryStop(DeliveryStop stop, Long routeId, Long shipmentId) {
        if (stop == null) throw new IllegalArgumentException("DeliveryStop cannot be null");
        validateData(stop.getSequence(), stop.getAddress(), stop.getStatus(), stop.getEta());
        Route route = routeService.getById(routeId);
        Shipment shipment = shipmentService.getById(shipmentId);
        if (deliveryStopRepository.existsByRoute_IdAndSequenceAndIsActiveTrue(routeId, stop.getSequence())) {
            throw new IllegalArgumentException("Duplicate sequence number on the same route");
        }
        stop.setRoute(route);
        stop.setShipment(shipment);
        stop.setActive(true);
        stop.setCreatedDate(new Date());
        return deliveryStopRepository.save(stop);
    }

    public List<DeliveryStop> getAllDeliveryStops() {
        return deliveryStopRepository.findAll().stream().filter(DeliveryStop::isActive).toList();
    }

    public DeliveryStop getById(Long id) {
        if (id == null || id <= 0) throw new IllegalArgumentException("DeliveryStop ID must be greater than zero");
        DeliveryStop stop = deliveryStopRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DeliveryStop not found with id: " + id));
        if (!stop.isActive()) throw new ResourceNotFoundException("DeliveryStop not found with id: " + id);
        return stop;
    }

    public DeliveryStop updateDeliveryStop(Long id, Integer sequence, String address, String status, Date eta,
                                           Long routeId, Long shipmentId) {
        validateData(sequence, address, status, eta);
        DeliveryStop stop = getById(id);
        if ((!stop.getRoute().getId().equals(routeId) || !stop.getSequence().equals(sequence))
                && deliveryStopRepository.existsByRoute_IdAndSequenceAndIsActiveTrue(routeId, sequence)) {
            throw new IllegalArgumentException("Duplicate sequence number on the same route");
        }
        stop.setSequence(sequence);
        stop.setAddress(address);
        stop.setStatus(status);
        stop.setEta(eta);
        stop.setRoute(routeService.getById(routeId));
        stop.setShipment(shipmentService.getById(shipmentId));
        stop.setUpdatedDate(new Date());
        return deliveryStopRepository.save(stop);
    }

    public DeliveryStop markComplete(Long deliveryStopId) {
        DeliveryStop stop = getById(deliveryStopId);
        stop.setStatus("Complete");
        stop.setUpdatedDate(new Date());
        DeliveryStop saved = deliveryStopRepository.save(stop);

        Long routeId = saved.getRoute().getId();
        long allStops = deliveryStopRepository.countByRoute_IdAndIsActiveTrue(routeId);
        long completedStops = deliveryStopRepository.countByRoute_IdAndStatusIgnoreCaseAndIsActiveTrue(routeId, "Complete");
        if (allStops > 0 && allStops == completedStops) {
            routeService.markComplete(routeId);
        }
        return saved;
    }

    public boolean deleteById(Long id) {
        DeliveryStop stop = getById(id);
        stop.setActive(false);
        stop.setUpdatedDate(new Date());
        deliveryStopRepository.save(stop);
        return true;
    }
}
