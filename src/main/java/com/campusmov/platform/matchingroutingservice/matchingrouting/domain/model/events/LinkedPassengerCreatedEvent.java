package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.events;

import com.campusmov.platform.matchingroutingservice.shared.domain.model.events.DomainEvent;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public final class LinkedPassengerCreatedEvent extends ApplicationEvent implements DomainEvent {
    private final String linkedPassengerId;
    private final String carpoolId;
    private final String passengerId;
    private final String driverId;
    private final String status;

    public LinkedPassengerCreatedEvent(
            String linkedPassengerId,
            String carpoolId,
            String passengerId,
            String driverId,
            String status
    ) {
        super("LinkedPassengerService");
        this.linkedPassengerId = linkedPassengerId;
        this.carpoolId = carpoolId;
        this.passengerId = passengerId;
        this.driverId = driverId;
        this.status = status;
    }
}
