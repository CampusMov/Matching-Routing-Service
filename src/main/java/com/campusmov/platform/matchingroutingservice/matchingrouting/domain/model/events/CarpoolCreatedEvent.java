package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.events;

import com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.valueobjects.Location;
import com.campusmov.platform.matchingroutingservice.shared.domain.model.events.DomainEvent;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CarpoolCreatedEvent extends ApplicationEvent implements DomainEvent {
    private final String carpoolId;
    private final String driverId;
    private final String vehicleId;
    private final String scheduleId;
    private final String status;
    private final Integer radius;
    private final Location origin;
    private final Location destination;
    private final Boolean isVisible;

    public CarpoolCreatedEvent(
            String carpoolId,
            String driverId,
            String vehicleId,
            String scheduleId,
            String status,
            Integer radius,
            Location origin,
            Location destination,
            Boolean isVisible
    ) {
        super("CarpoolService");
        this.carpoolId = carpoolId;
        this.driverId = driverId;
        this.vehicleId = vehicleId;
        this.scheduleId = scheduleId;
        this.status = status;
        this.radius = radius;
        this.origin = origin;
        this.destination = destination;
        this.isVisible = isVisible;
    }
}
