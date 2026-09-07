package com.example.LogisticsNetworkSystem.controllers;

import com.example.LogisticsNetworkSystem.dtos.TrackingEventDTO;
import com.example.LogisticsNetworkSystem.entities.TrackingEvent;
import com.example.LogisticsNetworkSystem.services.TrackingEventService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Exposes REST operations for shipment tracking event management. */
@RestController
@RequestMapping("/trackingEvent")
public class TrackingEventController {
    /** Coordinates tracking event persistence and shipment history through the service layer. */
    private final TrackingEventService trackingEventService;

    public TrackingEventController(TrackingEventService trackingEventService) {
        this.trackingEventService = trackingEventService;
    }

    /** Maps event details from the request DTO into a service-layer entity. */
    private TrackingEvent fromDTO(TrackingEventDTO dto) {
        TrackingEvent event = new TrackingEvent();
        event.setEventTime(dto.getEventTime());
        event.setLocation(dto.getLocation());
        event.setStatus(dto.getStatus());
        event.setNote(dto.getNote());
        return event;
    }

    @PostMapping("/add")
    public TrackingEventDTO add(@Valid @RequestBody TrackingEventDTO dto) {
        return TrackingEventDTO.convertToDTO(
                trackingEventService.addTrackingEvent(fromDTO(dto), dto.getShipmentId()));
    }

    @PostMapping("/append")
    public TrackingEventDTO append(@Valid @RequestBody TrackingEventDTO dto) {
        return TrackingEventDTO.convertToDTO(
                trackingEventService.appendTrackingEvent(fromDTO(dto), dto.getShipmentId()));
    }

    @GetMapping("/getAll")
    public List<TrackingEventDTO> getAll() {
        return TrackingEventDTO.convertToDTO(trackingEventService.getAllTrackingEvents());
    }

    @GetMapping("/getById/{id}")
    public TrackingEventDTO getById(@PathVariable Long id) {
        return TrackingEventDTO.convertToDTO(trackingEventService.getById(id));
    }

    @PutMapping("/update/{id}")
    public TrackingEventDTO update(@PathVariable Long id, @Valid @RequestBody TrackingEventDTO dto) {
        return TrackingEventDTO.convertToDTO(trackingEventService.updateTrackingEvent(
                id, dto.getEventTime(), dto.getLocation(), dto.getStatus(), dto.getNote(), dto.getShipmentId()));
    }

    @DeleteMapping("/delete/{id}")
    public TrackingEventDTO delete(@PathVariable Long id) {
        TrackingEventDTO dto = TrackingEventDTO.convertToDTO(trackingEventService.getById(id));
        trackingEventService.deleteById(id);
        return dto;
    }
}
