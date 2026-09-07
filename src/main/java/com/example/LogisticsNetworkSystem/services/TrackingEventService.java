package com.example.LogisticsNetworkSystem.services;

import com.example.LogisticsNetworkSystem.entities.Shipment;
import com.example.LogisticsNetworkSystem.entities.TrackingEvent;
import com.example.LogisticsNetworkSystem.exceptions.ResourceNotFoundException;
import java.util.Date;
import java.util.List;

import com.example.LogisticsNetworkSystem.repositories.TrackingEventRepository;
import org.springframework.stereotype.Service;

@Service
public class TrackingEventService {
    private final TrackingEventRepository trackingEventRepository;
    private final ShipmentService shipmentService;

    public TrackingEventService(
            TrackingEventRepository trackingEventRepository,
            ShipmentService shipmentService) {
        this.trackingEventRepository = trackingEventRepository;
        this.shipmentService = shipmentService;
    }

    private void validateData(Date eventTime, String location, String status, String note) {
        if (eventTime == null) {
            throw new IllegalArgumentException("Event time cannot be null");
        }
        if (eventTime.after(new Date())) throw new IllegalArgumentException("Event time cannot be in the future");
        if (location == null || location.trim().isEmpty()) {
            throw new IllegalArgumentException("Location cannot be blank");
        }
        if (location.length() > 255) throw new IllegalArgumentException("Location cannot exceed 255 characters");
        if (status == null || status.trim().isEmpty()) throw new IllegalArgumentException("Status cannot be blank");
        if (status.length() > 255) throw new IllegalArgumentException("Status cannot exceed 255 characters");
        if (note == null || note.trim().isEmpty()) throw new IllegalArgumentException("Note cannot be blank");
        if (note.length() > 255) throw new IllegalArgumentException("Note cannot exceed 255 characters");
    }

    private void validateTrackingStatus(String status) {
        if (!("Picked Up".equalsIgnoreCase(status)
                || "In Transit".equalsIgnoreCase(status)
                || "Delivered".equalsIgnoreCase(status))) {
            throw new IllegalArgumentException("Tracking status must be Picked Up, In Transit, or Delivered");
        }
    }

    public TrackingEvent addTrackingEvent(TrackingEvent event, Long shipmentId) {
        if (event == null) {
            throw new IllegalArgumentException("TrackingEvent cannot be null");
        }
        validateData(event.getEventTime(), event.getLocation(), event.getStatus(), event.getNote());
        event.setShipment(shipmentService.getById(shipmentId));
        event.setActive(true);
        event.setCreatedDate(new Date());
        return trackingEventRepository.save(event);
    }

    public TrackingEvent appendTrackingEvent(TrackingEvent event, Long shipmentId) {
        validateData(event.getEventTime(), event.getLocation(), event.getStatus(), event.getNote());
        validateTrackingStatus(event.getStatus());
        Shipment shipment = shipmentService.getById(shipmentId);
        event.setShipment(shipment);
        event.setActive(true);
        event.setCreatedDate(new Date());
        TrackingEvent saved = trackingEventRepository.save(event);
        shipmentService.updateStatus(shipmentId, event.getStatus());
        return saved;
    }

    public List<TrackingEvent> getAllTrackingEvents() {
        return trackingEventRepository.findAll().stream()
                .filter(TrackingEvent::isActive)
                .toList();
    }

    public TrackingEvent getById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("TrackingEvent ID must be greater than zero");
        }
        TrackingEvent event = trackingEventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TrackingEvent not found with id: " + id));
        if (!event.isActive()) throw new ResourceNotFoundException("TrackingEvent not found with id: " + id);
        return event;
    }

    public TrackingEvent updateTrackingEvent(
            Long id,
            Date eventTime,
            String location,
            String status,
            String note,
            Long shipmentId) {
        validateData(eventTime, location, status, note);
        TrackingEvent event = getById(id);
        event.setEventTime(eventTime);
        event.setLocation(location);
        event.setStatus(status);
        event.setNote(note);
        event.setShipment(shipmentService.getById(shipmentId));
        event.setUpdatedDate(new Date());
        return trackingEventRepository.save(event);
    }

    public boolean deleteById(Long id) {
        TrackingEvent event = getById(id);
        event.setActive(false);
        event.setUpdatedDate(new Date());
        trackingEventRepository.save(event);
        return true;
    }
}
