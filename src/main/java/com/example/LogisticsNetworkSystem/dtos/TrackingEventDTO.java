package com.example.LogisticsNetworkSystem.dtos;

import com.example.LogisticsNetworkSystem.entities.TrackingEvent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TrackingEventDTO {

    private Long id;

    @NotNull(message = "eventTime cannot be null")
    @PastOrPresent(message = "eventTime cannot be in the future")
    private Date eventTime;

    @NotBlank(message = "location cannot be blank")
    @Size(max = 255, message = "location cannot exceed 255 characters")
    private String location;

    @NotBlank(message = "status cannot be blank")
    @Size(max = 255, message = "status cannot exceed 255 characters")
    private String status;

    @NotBlank(message = "note cannot be blank")
    @Size(max = 255, message = "note cannot exceed 255 characters")
    private String note;

    @NotNull(message = "shipmentId cannot be null")
    @Positive(message = "shipmentId must be greater than zero")
    private Long shipmentId;

    @Builder
    public TrackingEventDTO(
            Long id,
            Date eventTime,
            String location,
            String status,
            String note,
            Long shipmentId) {
        this.id = id;
        this.eventTime = eventTime;
        this.location = location;
        this.status = status;
        this.note = note;
        this.shipmentId = shipmentId;
    }

    public static TrackingEventDTO convertToDTO(TrackingEvent entity) {
        if (entity == null) {
            return null;
        }
        return TrackingEventDTO.builder()
.id(entity.getId())
                .eventTime(entity.getEventTime())
                .location(entity.getLocation())
                .status(entity.getStatus())
                .note(entity.getNote())
                .shipmentId(entity.getShipment() == null ? null : entity.getShipment().getId())
                .build();
    }

    public static List<TrackingEventDTO> convertToDTO(List<TrackingEvent> entities) {
        return entities.stream()
                .map(TrackingEventDTO::convertToDTO)
                .collect(Collectors.toList());
    }
}
