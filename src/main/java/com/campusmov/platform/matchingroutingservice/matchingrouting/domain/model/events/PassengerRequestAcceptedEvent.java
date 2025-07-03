package com.campusmov.platform.matchingroutingservice.matchingrouting.domain.model.events;

import com.campusmov.platform.matchingroutingservice.shared.domain.model.events.DomainEvent;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public final class PassengerRequestAcceptedEvent extends ApplicationEvent implements DomainEvent {
    private final String passengerRequestId;
    private final String carpoolId;
    private final String passengerId;
    private final String pickupLocationName;
    private final String pickupLocationAddress;
    private final Double pickupLocationLongitude;
    private final Double pickupLocationLatitude;
    private final Integer requestedSeats;
    private final String status;

    public PassengerRequestAcceptedEvent(
            String passengerRequestId,
            String carpoolId,
            String passengerId,
            String pickupLocationName,
            String pickupLocationAddress,
            Double pickupLocationLongitude,
            Double pickupLocationLatitude,
            Integer requestedSeats,
            String status
    ) {
        super("PassengerRequestService");
        this.passengerRequestId = passengerRequestId;
        this.carpoolId = carpoolId;
        this.passengerId = passengerId;
        this.pickupLocationName = pickupLocationName;
        this.pickupLocationAddress = pickupLocationAddress;
        this.pickupLocationLongitude = pickupLocationLongitude;
        this.pickupLocationLatitude = pickupLocationLatitude;
        this.requestedSeats = requestedSeats;
        this.status = status;
    }
}